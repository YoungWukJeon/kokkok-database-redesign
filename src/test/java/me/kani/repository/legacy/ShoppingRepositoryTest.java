package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.ShoppingEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("쇼핑 데이터 가져오기")
    public void findAllTest() {
        final var shoppingRepository = new ShoppingRepository(legacyConnection);

        shoppingRepository.findAll()
                .stream()
                .map(ShoppingEntity::name)
                .sorted()
                .forEach(System.out::println);
    }
}