package com.dd.playgame;

public class BasketballPlayer extends Player {

    public double currentStrength;

    public double currentDefense;

    public double endurance;

    public BasketballPlayer() {
    }

    public BasketballPlayer(Player player) {
        this.num = player.num;
        this.name = player.name;
        this.initStrength = player.initStrength;
        this.initDefense = player.initDefense;
        this.currentStrength = this.initStrength;
        this.currentDefense = this.initDefense;
        this.endurance = BasketBallConfig.ENDURANCE_MAX;
        this.role = player.role;
    }

    public void addDefense(double defense) {
        this.currentDefense = Math.min(100.0, this.currentDefense + defense);
    }

    public void addStrength(double strength) {
        this.currentStrength = Math.min(100.0, this.currentStrength + strength);
    }

    public double getCurrentStrength() {
        return currentStrength;
    }

    public double getCurrentDefense() {
        return currentDefense;
    }

    public void addEndurance(double endurance) {
        this.endurance = Math.min(BasketBallConfig.ENDURANCE_MAX, this.endurance + endurance);
    }

    public void declineEndurance(double endurance) {
        this.endurance = Math.max(0, this.endurance - endurance);
    }

    public void refreshEndurance() {
        this.endurance = BasketBallConfig.ENDURANCE_MAX;
    }


    @Override
    public String toString() {
        return "Player{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", initStrength=" + initStrength +
                ", initDefense=" + initDefense +
                ", currentStrength=" + currentStrength +
                ", currentDefense=" + currentDefense +
                ", endurance=" + endurance +
                ", role=" + role +
                '}';
    }
}
