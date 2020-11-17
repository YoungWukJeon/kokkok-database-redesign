package me.kani.entity.legacy;

import me.kani.entity.legacy.types.AreaKind;

import java.util.Objects;

public record AreaEntity(String areaNo, String name, String address,
                         double latitude, double longitude,
                         String phone, String intro, String time, String cost, AreaKind areaKind) {
    public AreaEntity {
        Objects.requireNonNull(areaNo);
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
}