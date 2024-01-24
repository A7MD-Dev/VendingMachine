package system.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.api.request.BuyRequest;
import system.api.response.PurchaseResponse;
import system.exception.*;
import system.model.Product;
import system.model.Purchase;
import system.model.Role;
import system.model.User;
import system.service.ProductService;
import system.service.PurchaseService;
import system.service.UserService;
import system.api.response.ErrorResponse;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @Autowired
    public UserController(UserService userService,ProductService productService,PurchaseService purchaseService) {
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            log.error("No Users Found..");
            throw new NoUsersFoundException("No Users Found..");
        }
        log.info("Listing Users..");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + id + " Not Found..");
        }
        log.info("Getting User by ID..");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            log.info("User Created Successfully: {}", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UsernameAlreadyExistsException e) {
            log.error("Failed Create User-> Duplicated Username {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Duplicated Username", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.findUserById(id);
        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + id + " Not Found..");
        }
        log.info("Updating User..");
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + id + " Not Found..");
        }
        log.info("Deleting User..");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully ^^");
    }

    @PostMapping("/deposit/{userId}")
    public ResponseEntity<User> deposit(@PathVariable Long userId, @RequestBody Integer amount) throws BadRequestException {
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + user + " Not Found..");
        }
        if (!userService.isValidDepositAmount(amount)) {
            throw new InvalidDepositAmountException("Invalid deposit amount. Only 5, 10, 20, 50 and 100 cent coins are accepted..");
        }

        log.info("Deposit Updating..");
        user.setDeposit(amount + user.getDeposit());
        userService.updateUser(userId,user);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/buy")
    public ResponseEntity<PurchaseResponse> buy(@RequestBody @Valid BuyRequest buyRequest) throws Exception {
        Long userId = buyRequest.getUserId();
        User user = userService.findUserById(userId);

        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + user + " Not Found..");
        }

        if (!user.getRole().equals(Role.BUYER)) {
            log.error("User Not Allow to Buy");
            throw new NotAllowedException("Only buyers can purchase products..");
        }

        Product product = productService.findProductById(buyRequest.getProductId());
        if (product == null) {
            log.error("Product Not Found");
            throw new ProductNotFoundException("Product Not Found");
        }
        if (buyRequest.getQuantity() <= 0 || buyRequest.getQuantity() > product.getAmountAvailable()) {
            log.error("Invalid Quantity!");
            throw new InvalidQuantityException("Invalid quantity. Out of Stock!");
        }

        Integer totalPrice = (product.getCost() * buyRequest.getQuantity());
        if (user.getDeposit() < totalPrice) {
            log.error("Deposit Amount Not Accepted");
            throw new WrongDepositException("Please deposit more funds..");
        }

        user.setDeposit(user.getDeposit() - totalPrice);
        product.setAmountAvailable(product.getAmountAvailable() - buyRequest.getQuantity());
        productService.createProduct(product);
        Purchase purchase = purchaseService.createPurchase(user, product, buyRequest.getQuantity(), totalPrice);

        Map<Integer, Integer> change = userService.calculateChange(user.getDeposit(), totalPrice);
        user.setDeposit(0);
        userService.updateUser(userId,user);

        log.info("New Purchase..");
        return ResponseEntity.ok(new PurchaseResponse(purchase, change));
    }


    @PostMapping("/deposit/reset/{userId}")
    public ResponseEntity<?> reset(@PathVariable Long userId) throws AccessDeniedException {
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User Not Found");
            throw new UserNotFoundException("User #" + user + " Not Found..");
        }
//        if (!user.getRole().equals(Role.BUYER)) {
//            throw new AccessDeniedException("Only buyers can reset their deposit");
//        }
        log.info("Resetting Deposit..");
        user.setDeposit(0);
        userService.updateUser(userId,user);
        return ResponseEntity.ok().build();
    }
}
