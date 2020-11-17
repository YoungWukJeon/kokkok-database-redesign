package me.kani.entity.renewal;

import java.util.Objects;

public record CostEntity(Long no, Long spotNo, String content, Integer price) {
    public CostEntity {
        Objects.requireNonNull(spotNo);
        Objects.requireNonNull(content);
    }
}