package me.kani.entity.legacy.model;

import java.util.Objects;

public record ParsedUrlDto(String itemId, String address) {
    public ParsedUrlDto {
        Objects.requireNonNull(itemId);
        Objects.requireNonNull(address);
    }
}
