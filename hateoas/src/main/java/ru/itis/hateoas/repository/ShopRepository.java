package ru.itis.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.model.Shop;

import java.util.Optional;

@RepositoryRestResource
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @RestResource(path = "byName", rel = "byName")
    Optional<Shop> findByName(@Param("name") String name);
}
