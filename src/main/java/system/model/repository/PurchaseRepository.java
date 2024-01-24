package system.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}