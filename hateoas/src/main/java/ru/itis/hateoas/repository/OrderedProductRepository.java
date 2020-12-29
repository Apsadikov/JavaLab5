package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.model.Order;
import ru.itis.hateoas.model.OrderedProduct;
import ru.itis.hateoas.model.Product;

import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<OrderedProduct> findByProductAndOrder(Product product, Order order);
}
