package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itis.hateoas.model.Order;
import ru.itis.hateoas.model.User;

import java.util.List;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUser(User user);
}
