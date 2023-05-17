package com.dd.playgame.bean;

import com.dd.playgame.application.ConsumableType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Consumable implements Serializable {

    private static final long serialVersionUID = 1L;

    public int num;

    public String name;

    public String description;

    public ConsumableType type;

    public double effect;

    public String getEffectStr(){
        return BigDecimal.valueOf(effect)
                .setScale(2, RoundingMode.FLOOR)
                .toPlainString();
    }

    public String formatBasic() {
        return String.format("%-24s effect:%-11s",
                name, getEffectStr());
    }

    @Override
    public String toString() {
        return " num=" + num +
                ", name='" + name + '\'' +
                ", effect=" + effect;
    }
}
