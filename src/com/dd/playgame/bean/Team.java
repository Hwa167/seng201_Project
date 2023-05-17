package com.dd.playgame.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is a Java Bean class for representing a team.
 * It includes properties for the team's name, players, reserves, consumables,
 * initAmount, amount, integral, winCount and battleCount.
 */
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * team name
     */
    public String name;

    /**
     * All athletes on active
     */
    public List<Player> players;

    /**
     * All athletes on reserve
     */
    public List<Player> reserves;

    /**
     * All consumables
     */
    public List<Consumable> consumables;

    /**
     * Game initial money
     */
    public double initAmount;

    /**
     * current money
     */
    public double amount;

    /**
     * current points
     */
    public int integral;

    /**
     * Number of victories in the competition
     */
    public int winCount;

    /**
     * Total number of matches
     */
    public int battleCount;

    public Team() {
        this.players = new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.integral = 0;
        this.winCount = 0;
        this.battleCount = 0;
    }

    /**
     * Add points
     *
     * @param integral points
     */
    public void addIntegral(int integral) {
        this.integral += integral;
        this.battleCount++;
    }

    /**
     * Team receives bonus
     *
     * @param bonus money
     */
    public void addBonus(double bonus) {
        this.amount += bonus;
        this.winCount++;
    }

    /**
     * Purchase consumables
     *
     * @param consumable consumable
     * @return
     */
    public boolean buyConsumable(MarketConsumable consumable) {
        consumables.add(consumable);
        amount = amount - consumable.price;
        return true;
    }

    /**
     * Join a new athlete in the team
     *
     * @param player    athlete
     * @param choseUnit join the playing list or the reserve list
     * @return
     */
    public boolean buyPlayer(MarketPlayer player, int choseUnit) {
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

    /**
     * Refresh all athletes' endurance
     */
    public void refreshPlayerState() {
        for (Player player : players) {
            player.refreshEndurance();
        }
        for (Player player : reserves) {
            player.refreshEndurance();
        }
    }

    /**
     * Obtain interface display amount
     *
     * @return
     */
    public String getAmountStr() {
        return new BigDecimal(amount).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * Using consumables for athletes
     *
     * @param player     player
     * @param consumable consumable
     * @return tips
     */
    public String useConsumable(Player player, Consumable consumable) {
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

    /**
     * Selling reserve players
     *
     * @param player player
     */
    public void sellPlayer(Player player) {
        reserves.remove(player);
        this.amount = this.amount + ((MarketPlayer) player).getSellPrice();
    }

    /**
     * Selling consumables
     *
     * @param consumable consumable
     */
    public void sellConsumable(Consumable consumable) {
        consumables.remove(consumable);
        this.amount = this.amount + ((MarketConsumable) consumable).getSellPrice();
    }

    /**
     * Calculate the total score of team members
     *
     * @return score
     */
    public BigDecimal calculateTeamScore() {
        BigDecimal score = BigDecimal.ZERO;
        for (Player player : players) {
            score = score.add(BigDecimal.valueOf(player.calculateScore()));
        }
        return score.setScale(2, RoundingMode.UP);
    }

    /**
     * Score in string form
     *
     * @return
     */
    public String teamScoreStr() {
        return calculateTeamScore().toPlainString();
    }

    /**
     * Format Team Basic Information
     *
     * @return
     */
    public String formatBasic() {
        return String.format("Team: %-20s  Player Comprehensive score:%12s",
                name, teamScoreStr());
    }
}
