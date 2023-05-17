package com.dd.playgame.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is a Java Bean class for representing a consumable.
 * It includes properties for the consumable's number, name, description, type and effect.
 */
public class Consumable implements Serializable {

    private static final long serialVersionUID = 1L;

    public int num;

    public String name;

    public String description;

    public ConsumableType type;

    public double effect;

    /**
     * Obtain the formatted consumable effect for display
     * @return
     */
    public String getEffectStr(){
        return BigDecimal.valueOf(effect)
                .setScale(2, RoundingMode.FLOOR)
                .toPlainString();
    }

    /**
     * Obtain the formatted basic attributes of consumable for display
     * @return
     */
    public String formatBasic() {
        return String.format("%-30s effect:%-16s %s",
                name, getEffectStr(), description);
    }

    @Override
    public String toString() {
        return " num=" + num +
                ", name='" + name + '\'' +
                ", effect=" + effect;
    }
}
