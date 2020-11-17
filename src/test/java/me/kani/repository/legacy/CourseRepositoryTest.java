package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.CourseEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class CourseRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("코스 데이터 가져오기")
    public void findAllTest() {
        final var courseRepository = new CourseRepository(legacyConnection);

        courseRepository.findAll()
                .stream()
                .map(CourseEntity::name)
                .sorted()
                .forEach(System.out::println);
    }
}