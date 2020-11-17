package me.kani.repository.legacy;

import me.kani.Connector;
import me.kani.entity.legacy.ImageEntity;
import me.kani.entity.legacy.types.ImageKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();

    @Test
    @DisplayName("이미지 데이터 가져오기")
    public void findAllTest() {
        final var imageRepository = new ImageRepository(legacyConnection);

        imageRepository.findAll()
                .stream()
                .filter(e -> e.kind() == ImageKind.SHOPPING)
                .map(ImageEntity::itemId)
                .forEach(System.out::println);
    }
}