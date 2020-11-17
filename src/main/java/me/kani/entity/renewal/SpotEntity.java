package me.kani.entity.renewal;

import me.kani.entity.renewal.types.Category;

import java.time.LocalDateTime;
import java.util.Objects;

public record SpotEntity(Long no, String areaName, Category category,
                         String name, String address, Double latitude, Double longitude,
                         String intro, String url, String tags,
                         LocalDateTime createDate, Long creator,
                         LocalDateTime updateDate, Long updater) {
    public SpotEntity {
        Objects.requireNonNull(areaName);
        Objects.requireNonNull(category);
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);
        Objects.requireNonNull(createDate);
        Objects.requireNonNull(creator);
        Objects.requireNonNull(updateDate);
        Objects.requireNonNull(updater);
    }
}
