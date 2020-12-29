package ru.itis.hateoas.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String logo;

    private String about;

    @OneToOne
    @JoinColumn(name="userId", unique = true, nullable = false, updatable = false)
    private User user;
}
