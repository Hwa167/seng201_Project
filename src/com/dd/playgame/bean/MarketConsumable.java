package com.dd.playgame.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Consumable sold on the market
 * It includes properties for the consumable's basic and market price;
 */
public class MarketConsumable extends Consumable {

    private static final long serialVersionUID = 1L;

    /**
     * consumable price
     */
    public double price;

    /**
     * Price of consumables for display
     *
     * @return String format price
     */
    public String getPriceStr() {
        return BigDecimal.valueOf(price)
                .setScale(2, RoundingMode.FLOOR)
                .toPlainString();
    }

    /**
     * Commodity recycling price
     *
     * @return price
     */
    public double getSellPrice() {
        return price * 0.8;
    }

    /**
     * Recycling Price of consumables for display
     *
     * @return String format price
     */
    public String getSellPriceStr() {
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

    /**
     * Format market consumable information
     *
     * @return info
     */
    public String format() {
        return String.format("Consumable%-5s %-24s effect:%-11s price:%-11s", "",
                name, getEffectStr(), getPriceStr());
    }
}
