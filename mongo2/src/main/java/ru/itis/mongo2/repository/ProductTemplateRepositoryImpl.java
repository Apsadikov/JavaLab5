package ru.itis.mongo2.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.itis.mongo2.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductTemplateRepositoryImpl implements CrudRepository<Product, String> {
    private MongoTemplate mongoTemplate;

    @Autowired
    public ProductTemplateRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> findAll() {
        return mongoTemplate.findAll(Product.class, "product");
    }

    @Override
    public Optional<Product> findById(String s) {
        return Optional.of(mongoTemplate.findById(s, Product.class, "product"));
    }

    @Override
    public void delete(String s) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(s)), "product");
    }

    @Override
    public Product save(Product entity) {
        return mongoTemplate.save(entity, "product");
    }
}
