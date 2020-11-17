package me.kani.entity.renewal;

import java.util.Objects;

public record ConvenienceEntity(Long no, Long spotNo, String content) {
    public ConvenienceEntity {
        Objects.requireNonNull(spotNo);
        Objects.requireNonNull(content);
    }
}
