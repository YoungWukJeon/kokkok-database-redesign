package me.kani.entity.renewal;

import me.kani.entity.renewal.types.ContactType;

import java.util.Objects;

public record ContactEntity(Long no, Long spotNo, ContactType type, String phone) {
    public ContactEntity {
        Objects.requireNonNull(spotNo);
        Objects.requireNonNull(type);
        Objects.requireNonNull(phone);
    }
}
