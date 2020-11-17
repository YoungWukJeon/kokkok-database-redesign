package me.kani;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<E, T> {
    List<E> findAll();
    default Optional<E> findById(T id) {
        return Optional.empty();
    }

    default void save(E entity) {}
    default void saveAll(List<E> entities) {}
}
