package me.kani.entity.renewal;

import me.kani.entity.renewal.types.State;

import java.time.LocalDateTime;
import java.util.Objects;

public record SpotImageEntity(Long no, Long spotNo, String url,
                              Boolean representative, State state,
                              LocalDateTime createDate, Long creator) {
    public SpotImageEntity {
        Objects.requireNonNull(spotNo);
        Objects.requireNonNull(url);
        Objects.requireNonNull(representative);
        Objects.requireNonNull(state);
        Objects.requireNonNull(createDate);
        Objects.requireNonNull(creator);
    }
}
