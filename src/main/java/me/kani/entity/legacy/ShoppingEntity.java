package me.kani.entity.legacy;

import java.util.Objects;

public record ShoppingEntity(String shopNo, String name, String phone, String address,
                             double latitude, double longitude, String intro, String time) {
    public ShoppingEntity {
        Objects.requireNonNull(shopNo);
        Objects.requireNonNull(name);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(address);
    }
}
