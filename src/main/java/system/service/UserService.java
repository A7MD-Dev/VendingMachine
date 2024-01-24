package system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.exception.UserNotFoundException;
import system.exception.UsernameAlreadyExistsException;
import system.model.User;
import system.model.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setDeposit(updatedUser.getDeposit());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean isValidDepositAmount(Integer amount) {
        return amount % 5 == 0 && amount <= 100;
    }

    public Map<Integer, Integer> calculateChange(Integer deposit, Integer totalPrice) {
        Map<Integer, Integer> change = new HashMap<>();
        int remainingAmount =  Math.round((deposit - totalPrice) * 100);
        int[] coinValues = {100, 50, 20, 10, 5};

        for (int coinValue : coinValues) {
            int numCoins = remainingAmount / coinValue;
            if (numCoins > 0) {
                change.put(coinValue, numCoins);
                remainingAmount -= numCoins * coinValue;
            }
        }

        return change;
    }

}
