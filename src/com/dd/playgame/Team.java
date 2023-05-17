package com.dd.playgame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Team {

    public String name;

    public List<BasketballPlayer> players;

    public List<BasketballPlayer> reserves;

    public List<BasketballPlayer> damages;

    public List<Consumable> consumables;

    public double initAmount;

    public double amount;

    //比赛积分
    private int integral;

    public Team() {
        this.players = new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.damages = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.integral = 0;
    }

    public String getAmount() {
        return new BigDecimal(amount).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    public void addIntegral(int integral){
        this.integral += integral;
    }

    public int getIntegral() {
        return integral;
    }

    public void addBonus(double bonus){
        this.amount += bonus;
    }

    public void recast(){
        List<BasketballPlayer> fatigues = players.stream().filter(item -> item.endurance == 0d).collect(Collectors.toList());
        if (!fatigues.isEmpty()) {
            reserves.addAll(fatigues);
            players.removeAll(fatigues);

            List<PlayerRole> roles = fatigues.stream().map(item -> item.role).collect(Collectors.toList());
            for (PlayerRole role : roles) {
                findPlayer(role).ifPresent(player -> {
                    reserves.remove(player);
                    players.add(player);
                });
            }
        }
    }

    public void refreshPlayerState(){
        for (BasketballPlayer player : players) {
            player.refreshEndurance();
        }
        for (BasketballPlayer player : reserves) {
            player.refreshEndurance();
        }
    }

    public Optional<BasketballPlayer> findPlayer(PlayerRole role) {
        return reserves.stream().filter(item -> item.endurance > 0d && item.role == role).findFirst();
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", reserves=" + reserves +
                ", damages=" + damages +
                ", initAmount=" + initAmount +
                ", amount=" + amount +
                '}';
    }
}
