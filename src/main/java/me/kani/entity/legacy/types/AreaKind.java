package me.kani.entity.legacy.types;

public enum AreaKind {
    TOURISM, LEISURE, CULTURE;

    public static AreaKind convert(String kind) {
        return switch (kind) {
            case "tourism" -> TOURISM;
            case "leisure" -> LEISURE;
            case "culture" -> CULTURE;
            default -> null;
        };
    }
}
