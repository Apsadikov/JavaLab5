package ru.itis.mongo2.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {
    List<E> findAll();

    Optional<E> findById(ID id);

    void delete(ID id);

    E save(E entity);
}
