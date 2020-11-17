package me.kani.entity.legacy.types;

public enum UrlKind {
    AREA, COURSE, REVIEW, MEMBER, RESTAURANT, ACCOMMODATION, SHOPPING;

    public static UrlKind convert(String kind) {
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

    public String revert() {
        return switch (this) {
            case AREA -> "area";
            case COURSE -> "course";
            case REVIEW -> "review";
            case MEMBER -> "member";
            case RESTAURANT -> "restaurant";
            case ACCOMMODATION -> "accommodation";
            case SHOPPING -> "shopping";
        };
    }
}
