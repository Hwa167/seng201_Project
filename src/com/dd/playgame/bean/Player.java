package com.dd.playgame.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is a Java Bean class for representing a player.
 * It includes properties for the player's number, name, strength, defense, endurance, role and description.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    public int num;
    public String name;
    public double strength;
    public double defense;
    public double endurance;
    public PlayerRole role;
    public String description;

    /**
     * initializing athletes
     */
    public Player() {
        //When initializing athletes, the endurance value defaults to full
        this.endurance = 100d;
    }

    /**
     * Calculate the ability score based on the athlete's current attributes
     *
     * @return
     */
    public double calculateScore() {
        return BigDecimal.valueOf(strength).add(BigDecimal.valueOf(defense))
                .add(BigDecimal.valueOf(endurance).multiply(BigDecimal.valueOf(0.5)))
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    /**
     * Refresh Athlete Endurance Values
     */
    public void refreshEndurance() {
        this.endurance = 100d;
    }

    /**
     * Reduce endurance
     *
     * @param endurance value
     */
    public void declineEndurance(double endurance) {
        this.endurance = Math.max(0, this.endurance - endurance);
    }

    /**
     * Supplementary endurance
     *
     * @param endurance value
     */
    public void addEndurance(double endurance) {
        this.endurance = Math.min(100d, this.endurance + endurance);
    }

    /**
     * Defense has been improved
     *
     * @param defense value
     */
    public void addDefense(double defense) {
        this.defense = Math.min(100.0, this.defense + defense);
    }

    /**
     * Strength has been improved
     *
     * @param strength value
     */
    public void addStrength(double strength) {
        this.strength = Math.min(100.0, this.strength + strength);
    }

    /**
     * Strength of player for display
     *
     * @return info
     */
    public String getStrengthStr() {
        return BigDecimal.valueOf(strength).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * Defense of player for display
     *
     * @return info
     */
    public String getDefenseStr() {
        return BigDecimal.valueOf(defense).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * Endurance of player for display
     *
     * @return info
     */
    public String getEnduranceStr() {
        return BigDecimal.valueOf(endurance).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * Obtain the formatted basic attributes of athlete for display
     * @return
     */
    public String formatBasic() {
        return String.format("%-13s strength:%-11s defense:%-11s endurance:%-11s role:%-23s",
                name, getStrengthStr(), getDefenseStr(), getEnduranceStr(), role.value);
    }
}
