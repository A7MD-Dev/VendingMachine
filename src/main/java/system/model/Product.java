package system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;


@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Column(name = "product_name")
    private String productName;

    @Min(value = 0, message = "Amount available must be non-negative")
    @Column(name = "amount_available")
    private Integer amountAvailable;

    @Min(value = 1, message = "Product cost must be positive")
    @Column(name = "cost")
    private Integer cost;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="seller_id", referencedColumnName="id")
    private User seller;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;


    public List<Purchase> getPurchases() {
        return purchases;
    }

    public Integer getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Integer getCost() {
        return cost;
    }

    public User getSeller() {
        return seller;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}