package com.dd.playgame.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;

    public List<Player> players;

    public List<Player> reserves;

    public List<Consumable> consumables;

    public double initAmount;

    public double amount;

    public int integral;

    public int winCount;

    public int battleCount;

    public Team() {
        this.players = new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.integral = 0;
        this.winCount = 0;
        this.battleCount = 0;
    }

    public void addIntegral(int integral) {
        this.integral += integral;
        this.battleCount++;
    }

    public void addBonus(double bonus) {
        this.amount += bonus;
        this.winCount++;
    }

    public boolean buyConsumable(MarketConsumable consumable) {
        consumables.add(consumable);
        amount = amount - consumable.price;
        return true;
    }

    public boolean joinPlayer(MarketPlayer player, int choseUnit) {
        if (choseUnit == 1) {
            Optional<Player> first = players.stream().filter(item -> item.role == player.role).findFirst();
            first.ifPresent(it -> {
                players.remove(it);
                reserves.add(it);
            });
            players.add(player);
        } else {
            reserves.add(player);
        }
        amount = amount - player.price;
        return true;
    }

    public void refreshPlayerState() {
        for (Player player : players) {
            player.refreshEndurance();
        }
        for (Player player : reserves) {
            player.refreshEndurance();
        }
    }

    public String getAmountStr() {
        return new BigDecimal(amount).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    public String useConsumable(Player player, int consumableIndex) {
        Consumable consumable = consumables.get(consumableIndex);
        switch (consumable.type) {
            case ENDURANCE:
                if (player.endurance < 100d) {
                    player.addEndurance(consumable.effect);
                    consumables.remove(consumable);
                    return "Successfully used, current endurance is " + player.getEnduranceStr();
                } else {
                    return "The endurance value has reached its maximum 100.00";
                }
            case STRENGTH:
                if (player.strength < 100d) {
                    player.addStrength(consumable.effect);
                    consumables.remove(consumable);
                    return "Successfully used, current strength is " + player.getStrengthStr();
                } else {
                    return "The strength value has reached its maximum 100.00";
                }
            case DEFENSE:
                if (player.defense < 100d) {
                    player.addDefense(consumable.effect);
                    consumables.remove(consumable);
                    return "Successfully used, current defense is " + player.getDefenseStr();
                } else {
                    return "The defense value has reached its maximum 100.00";
                }
        }
        return "unknown error!";
    }

    public void sellPlayer(Player player) {
        reserves.remove(player);
        this.amount = this.amount + ((MarketPlayer) player).getSellPrice();
    }

    public void sellConsumable(Consumable consumable) {
        consumables.remove(consumable);
        this.amount = this.amount + ((MarketConsumable) consumable).getSellPrice();
    }
}
