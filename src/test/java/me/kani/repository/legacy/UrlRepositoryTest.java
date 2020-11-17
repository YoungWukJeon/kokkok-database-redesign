package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.UrlEntity;
import me.kani.entity.legacy.types.UrlKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UrlRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("Url 데이터 가져오기")
    public void findAllTest() {
        final var urlRepository = new UrlRepository(legacyConnection);

        urlRepository.findAll()
                .stream()
                .filter(e -> e.kind() == UrlKind.AREA)
                .map(UrlEntity::itemId)
                .forEach(System.out::println);
    }
}