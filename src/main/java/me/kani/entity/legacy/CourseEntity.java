package me.kani.entity.legacy;

import java.util.Objects;

public record CourseEntity(String courseNo, String name, String route,
                           String isBasic, String nickname) {
    public CourseEntity {
        Objects.requireNonNull(courseNo);
        Objects.requireNonNull(name);
        Objects.requireNonNull(route);
        Objects.requireNonNull(isBasic);
    }
}
