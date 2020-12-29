package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.model.Product;

import java.util.List;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
    @RestResource(path = "byMinPrice", rel = "byMinPrice")
    @Query("from Product where price > :minPrice")
    List<Product> findByPriceMinPrice(@Param("minPrice") Double minPrice);
}
