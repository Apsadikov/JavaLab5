package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
