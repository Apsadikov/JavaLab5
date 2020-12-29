package ru.itis.hateoas.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class OrderedProduct {
    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name="orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;
}
