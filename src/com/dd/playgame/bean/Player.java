package com.dd.playgame.bean;

import com.dd.playgame.application.PlayerRole;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    public int num;

    public String name;

    public double strength;

    public double defense;

    public double endurance;

    public PlayerRole role;

    public String description;

    public Player() {
        this.endurance = 100d;
    }

    public double calculateScore() {
        return BigDecimal.valueOf(strength).add(BigDecimal.valueOf(defense))
                .add(BigDecimal.valueOf(endurance).multiply(BigDecimal.valueOf(0.5)))
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    public void refreshEndurance() {
        this.endurance = 100d;
    }

    public void declineEndurance(double endurance) {
        this.endurance = Math.max(0, this.endurance - endurance);
    }

    public void addEndurance(double endurance) {
        this.endurance = Math.min(100d, this.endurance + endurance);
    }

    public void addDefense(double defense) {
        this.defense = Math.min(100.0, this.defense + defense);
    }

    public void addStrength(double strength) {
        this.strength = Math.min(100.0, this.strength + strength);
    }

    public String getStrengthStr() {
        return BigDecimal.valueOf(strength).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    public String getDefenseStr() {
        return BigDecimal.valueOf(defense).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    public String getEnduranceStr() {
        return BigDecimal.valueOf(endurance).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    public String formatBasic() {
        return String.format("%-13s strength:%-11s defense:%-11s endurance:%-11s role:%-23s",
                name, getStrengthStr(), getDefenseStr(), getEnduranceStr(), role.value);
    }
}
