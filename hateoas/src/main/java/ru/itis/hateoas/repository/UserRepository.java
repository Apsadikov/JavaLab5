package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
