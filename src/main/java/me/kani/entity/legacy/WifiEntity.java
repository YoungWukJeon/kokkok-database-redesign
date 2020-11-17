package me.kani.entity.legacy;

import java.util.Objects;

public record WifiEntity(String wifiNo, String name,
                         double latitude, double longitude) {
    public WifiEntity {
        Objects.requireNonNull(wifiNo);
        Objects.requireNonNull(name);
    }
}