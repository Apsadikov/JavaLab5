package ru.itis.mongo3.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.itis.mongo3.model.Product;
import ru.itis.mongo3.model.Shop;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String>, QuerydslPredicateExecutor<Product> {
}
