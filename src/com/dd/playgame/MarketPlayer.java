package com.dd.playgame;

public class MarketPlayer extends Player {

    public double price;

    @Override
    public String toString() {
        return "MarketPlayer{" +
                "price=" + price +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", initStrength=" + initStrength +
                ", initDefense=" + initDefense +
                ", role=" + role +
                '}';
    }
}
