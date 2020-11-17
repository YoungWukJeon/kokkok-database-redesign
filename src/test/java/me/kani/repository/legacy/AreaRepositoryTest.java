package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.AreaEntity;
import me.kani.entity.legacy.types.AreaKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class AreaRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("지역 명소 데이터 가져오기")
    public void findAllTest() {
        final var areaRepository = new AreaRepository(legacyConnection);

        areaRepository.findAll()
                .stream()
                .filter(a -> a.areaKind() == AreaKind.CULTURE)
                .map(AreaEntity::name)
                .sorted()
                .forEach(System.out::println);
    }
}