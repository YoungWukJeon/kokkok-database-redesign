package me.kani.entity.legacy;

import java.util.Objects;

public record AccommodationEntity(String accNo, String name, String address,
                                  double latitude, double longitude, String phone, String intro, String subIntro) {
    public AccommodationEntity {
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
}
