package com.dd.playgame.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Player sold on the market
 * It includes properties for the player's basic and market price;
 */
public class MarketPlayer extends Player {

    private static final long serialVersionUID = 1L;

    /**
     * Player price
     */
    public double price;

    /**
     * Price of player for display
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
     * Recycling Price of player for display
     *
     * @return String format price
     */
    public String getSellPriceStr() {
        return BigDecimal.valueOf(getSellPrice()).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * Format market player information
     *
     * @return info
     */
    public String format() {
        return String.format("Player%-9s %-13s strength:%-11s defense:%-11s role:%-23s price:%-11s", "",
                name, getStrengthStr(), getDefenseStr(), role.value, getPriceStr());
    }

    @Override
    public String toString() {
        return "MarketPlayer{" +
                "price=" + price +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", strength=" + strength +
                ", defense=" + defense +
                ", endurance=" + endurance +
                ", role=" + role +
                ", description='" + description + '\'' +
                '}';
    }
}
