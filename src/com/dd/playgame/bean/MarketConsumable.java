package com.dd.playgame.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarketConsumable extends Consumable {

    private static final long serialVersionUID = 1L;

    public double price;

    public String getPriceStr() {
        return BigDecimal.valueOf(price)
                .setScale(2, RoundingMode.FLOOR)
                .toPlainString();
    }

    public double getSellPrice() {
        return price * 0.8;
    }

    public String getSellPriceStr(){
        return BigDecimal.valueOf(getSellPrice()).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    @Override
    public String toString() {
        return "MarketConsumable{" +
                "price=" + price +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", effect=" + effect +
                '}';
    }

    public String format() {
        return String.format("Consumable%-5s %-24s effect:%-11s price:%-11s", "",
                name, getEffectStr(), getPriceStr());
    }
}
