package me.kani.entity.renewal.types;

import me.kani.entity.legacy.types.AreaKind;

public enum Category {
    TOURIST_ATTRACTION, LEISURE, CULTURE, SHOPPING, ACCOMMODATION, RESTAURANT;

    public static Category convertFromAreaKind(AreaKind areaKind) {
        return switch (areaKind.name()) {
            case "TOURISM" -> TOURIST_ATTRACTION;
            case "CULTURE" -> CULTURE;
            case "LEISURE" -> LEISURE;
            default -> null;
        };
    }
}
