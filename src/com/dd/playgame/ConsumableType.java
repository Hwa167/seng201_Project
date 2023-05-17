package com.dd.playgame;

public enum ConsumableType {
    STRENGTH("力量药水", "增强力量"),
    DEFENSE("防御药水", "提升防御力"),
    ENDURANCE("耐力药水", "恢复耐力");

    public final String name;
    public final String description;

    ConsumableType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
