package ru.itis.mongo3.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.itis.mongo3.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>, QuerydslPredicateExecutor<Product> {
}
