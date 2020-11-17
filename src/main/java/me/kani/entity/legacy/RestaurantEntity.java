package me.kani.entity.legacy;

import java.util.Objects;

public record RestaurantEntity(String rstNo, String name, String address,
                               double latitude, double longitude, String phone, String intro,
                               String time, String dayOff, String menu) {
    public RestaurantEntity {
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
}
