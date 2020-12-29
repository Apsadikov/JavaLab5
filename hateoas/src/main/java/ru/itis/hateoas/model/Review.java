package ru.itis.hateoas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private String review;

    private int stars;
}
