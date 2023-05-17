package com.dd.playgame.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarketPlayer extends Player {

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
