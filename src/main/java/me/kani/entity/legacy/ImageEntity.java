package me.kani.entity.legacy;

import me.kani.entity.legacy.types.ImageKind;

import java.util.Objects;

public record ImageEntity(ImageKind kind, String itemId, String url) {
    public ImageEntity {
        Objects.requireNonNull(itemId);
        Objects.requireNonNull(url);
    }
}
