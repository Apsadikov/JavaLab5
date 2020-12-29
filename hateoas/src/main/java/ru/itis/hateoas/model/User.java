package ru.itis.hateoas.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(optional = true, mappedBy = "user")
    private Shop shop;
}
