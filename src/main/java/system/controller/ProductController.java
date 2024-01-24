package system.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.exception.NoProductsAvailableException;
import system.exception.ProductCreationFailedException;
import system.model.Product;
import system.exception.ProductNotFoundException;
import system.service.ProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            log.error("No Products Found..");
            throw new NoProductsAvailableException("No Products are Available..");
        } else {
            log.info("Listing Products..");
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.findProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            log.error("Product Not Found");
            throw e;
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
        } catch (Exception e) {
            log.info("Failed Creation {}",e.getMessage());
            throw new ProductCreationFailedException("Failed-> " + e.getMessage());
        }
    }

}
