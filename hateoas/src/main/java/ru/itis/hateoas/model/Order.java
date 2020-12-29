package ru.itis.hateoas.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String shippingAddress;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @OneToMany(mappedBy="order", fetch = FetchType.EAGER)
    private Set<OrderedProduct> orderedProducts;
}
