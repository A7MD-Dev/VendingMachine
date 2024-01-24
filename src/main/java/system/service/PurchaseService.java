package system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.model.Product;
import system.model.Purchase;
import system.model.User;
import system.model.repository.PurchaseRepository;

import java.time.LocalDateTime;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase createPurchase(User user, Product product, Integer quantity, Integer totalPrice) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setProduct(product);
        purchase.setQuantity(quantity);
        purchase.setTotalPrice(totalPrice);
        purchase.setPurchaseDate(LocalDateTime.now());
        return purchaseRepository.save(purchase);
    }


}
