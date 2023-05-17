package com.dd.playgame.application;

public enum PlayerRole {

    POINT_GUARD("point guard", "handling the ball and directing the team's offense."),
    SHOOTING_GUARD("shooting guard", "scoring points by shooting the ball and also assists the point guard in running the offense."),
    SMALL_FORWARD("small forward", "a combination of scoring, rebounding, and defending on both ends of the court."),
    POWER_FORWARD("power forward", "playing inside the paint, rebounding, defending, and scoring both inside and outside of the key"),
    CENTER("center", "playing in the low post, defending the rim, rebounding, and scoring inside the paint.");

    public final String value;
    public final String description;

    PlayerRole(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
