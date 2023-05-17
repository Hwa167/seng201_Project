package com.dd.playgame.generator;

import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.Player;
import com.dd.playgame.util.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Responsible for generating player
 */
public class PlayerGenerator {
    /**
     * Athlete Name
     */
    private static final String[] names = {"John", "David", "Michael", "Daniel", "Steven", "Robert", "William", "Christopher", "James", "Joseph", "Smith", "Johnson", "Brown", "Davis", "Wilson", "Taylor", "Anderson", "Thompson", "Harris", "Martin"};

    /**
     * Athlete Capability Range
     */
    private static final double[][] ATTR_RANGE = {
            {60d, 80d},   // easy
            {70d, 90d},   // normal
            {80d, 100d},   // hard
            {95d, 100d},
            {98d, 100d},
    };

    /**
     * Generate athletes based on game difficulty
     *
     * @param difficulty difficulty
     * @return {@link Player}
     */
    public static Player generatePlayer(int difficulty) {
        double strength = RandomUtils.getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);
        double defense = RandomUtils.getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);

        Player player = new Player();
        player.name = getRandomName();
        player.strength = strength;
        player.defense = defense;
        return player;
    }

    /**
     * Randomly select a name from an array
     *
     * @return name
     */
    public static String getRandomName() {
        return names[RandomUtils.getRandomInt(names.length - 1)];
    }

    /**
     * Generate athletes based on game difficulty
     *
     * @param difficulty difficulty
     * @return {@link  MarketPlayer}
     */
    public static MarketPlayer generateMarketPlayer(int difficulty) {
        double strength = RandomUtils.getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);
        double defense = RandomUtils.getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);

        MarketPlayer marketPlayer = new MarketPlayer();
        marketPlayer.strength = strength;
        marketPlayer.defense = defense;
        marketPlayer.price = calculatePrice(difficulty, marketPlayer);
        return marketPlayer;
    }

    /**
     * Calculate the selling price of athletes based on the difficulty of the game
     *
     * @param difficulty difficulty
     * @param player     player
     * @return price
     */
    private static double calculatePrice(int difficulty, Player player) {
        double sum = player.strength + player.defense;
        double ratio = sum / (ATTR_RANGE[difficulty][1] * 2);
        return BigDecimal.valueOf(ratio).multiply(BigDecimal.valueOf(100 + difficulty * 50))
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }
}
