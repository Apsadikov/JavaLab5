package ru.itis.hateoas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private Double price;

    private String name;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @ManyToMany
    @JoinTable(name="productReview",
            joinColumns = @JoinColumn(name="productId", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="reviewId", referencedColumnName="id")
    )
    private List<Review> reviews;
}
