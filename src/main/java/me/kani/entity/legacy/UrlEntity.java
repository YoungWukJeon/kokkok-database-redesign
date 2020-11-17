package me.kani.entity.legacy;

import me.kani.entity.legacy.types.UrlKind;

import java.util.Objects;

public record UrlEntity(UrlKind kind, String itemId, String address) {
    public UrlEntity {
        Objects.requireNonNull(itemId);
        Objects.requireNonNull(address);
    }
}
