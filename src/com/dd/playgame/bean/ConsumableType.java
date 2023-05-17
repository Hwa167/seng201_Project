package com.dd.playgame.bean;

/**
 * Consumable type, corresponding to athlete attributes
 */
public enum ConsumableType {
    STRENGTH("strength potion", "raise the strength"),
    DEFENSE("defense potion", "raise the defense"),
    ENDURANCE("endurance potion", "raise the endurance");

    public final String name;
    public final String description;

    ConsumableType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
