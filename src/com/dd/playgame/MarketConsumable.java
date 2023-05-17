package com.dd.playgame;

public class MarketConsumable extends Consumable {

    public double price;

    @Override
    public String toString() {
        return "MarketConsumable{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", effect=" + effect +
                '}';
    }
}
