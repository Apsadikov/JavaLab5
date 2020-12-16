package ru.itis.mongo2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.mongo2.model.Product;

@RepositoryRestResource
public interface ProductMongoRepository extends MongoRepository<Product, String> {
    @RestResource(path = "byPrice")
    @Query(value = "{ 'price': { $gt: '?0', $lt: '?1' } }")
    Page<Product> findAllByPrice(Pageable pageable, @Param("min") double min, @Param("max") double max);

    @RestResource(path = "byName")
    Page<Product> findAllByName(Pageable pageable, @Param("name") String name);
}
