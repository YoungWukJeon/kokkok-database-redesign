package me.kani.entity.renewal;

import me.kani.entity.renewal.types.OperatingTimeType;

import java.util.Objects;

public record OperatingTimeEntity(Long no, Long spotNo, OperatingTimeType operatingTimeType, String time) {
    public OperatingTimeEntity {
        Objects.requireNonNull(spotNo);
        Objects.requireNonNull(operatingTimeType);
    }
}
