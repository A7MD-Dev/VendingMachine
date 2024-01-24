package system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(name = "username", unique = true)
    private String username;


    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;

    @Min(value = 0, message = "Deposit cannot be negative")
    @Column(name = "deposit")
    private Integer deposit;


    @NotNull(message = "Role cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private Set<Product> sellerProducts;

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String password, Role role) {
        this.username=username;
        this.password=password;
        this.role=role;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Product> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(Set<Product> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }


}

