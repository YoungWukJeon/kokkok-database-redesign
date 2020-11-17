package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.AccommodationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AccommodationRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("숙박 데이터 가져오기")
    public void findAllTest() {
        final var accommodationRepository = new AccommodationRepository(legacyConnection);

        accommodationRepository.findAll()
                .stream()
                .map(AccommodationEntity::name)
                .sorted()
                .forEach(System.out::println);
    }
}