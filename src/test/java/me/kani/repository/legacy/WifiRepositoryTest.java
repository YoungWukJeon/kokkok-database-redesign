package me.kani.repository.legacy;

import me.kani.Connector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class WifiRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("Wifi 데이터 가져오기")
    public void findAllTest() {
        final var wifiRepository = new WifiRepository(legacyConnection);

        wifiRepository.findAll()
                .forEach(System.out::println);
    }
}