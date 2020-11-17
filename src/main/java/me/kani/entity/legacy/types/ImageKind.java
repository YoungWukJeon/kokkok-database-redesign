package me.kani.entity.legacy.types;

public enum ImageKind {
    AREA, COURSE, REVIEW, MEMBER, RESTAURANT, ACCOMMODATION, SHOPPING;

    public static ImageKind convert(String kind) {
        return switch (kind) {
            case "area" -> AREA;
            case "course" -> COURSE;
            case "review" -> REVIEW;
            case "member" -> MEMBER;
            case "restaurant" -> RESTAURANT;
            case "accommodation" -> ACCOMMODATION;
            case "shopping" -> SHOPPING;
            default -> null;
        };
    }
}
