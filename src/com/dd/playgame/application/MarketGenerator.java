package com.dd.playgame.application;

import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MarketGenerator {

    private static final double[][] ATTR_RANGE = {
            {60d, 80d},   // easy
            {70d, 90d},   // normal
            {80d, 100d},   // hard
            {95d, 100d},
            {98d, 100d},
    };

    private static final double[][] RAISE_RANGE = {
            {2d, 3d},   // easy
            {1d, 2d},  // norm
            {0.2, 1d}   // hard
    };

    private static double getRandomDouble(double min, double max) {
        double randomDouble = ThreadLocalRandom.current().nextDouble(min, max + 0.01);
        return BigDecimal.valueOf(randomDouble)
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    private static MarketPlayer generateMarketPlayer(int difficulty) {
        double strength = getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);
        double defense = getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);

        MarketPlayer marketPlayer = new MarketPlayer();
        marketPlayer.strength = strength;
        marketPlayer.defense = defense;
        marketPlayer.price = calculatePrice(difficulty, marketPlayer);
        return marketPlayer;
    }

    private static double calculatePrice(int difficulty, Player player) {
        double sum = player.strength + player.defense;
        double ratio = sum / (ATTR_RANGE[difficulty][1] * 2);
        return BigDecimal.valueOf(ratio).multiply(BigDecimal.valueOf(100 + difficulty * 50))
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    private static MarketConsumable generateMarketConsumable(int difficulty, ConsumableType consumableType) {
        double effect = getRandomDouble(RAISE_RANGE[difficulty][0], RAISE_RANGE[difficulty][1]);
        MarketConsumable consumable = new MarketConsumable();
        consumable.name = consumableType.name;
        consumable.type = consumableType;
        consumable.description = consumableType.description;
        consumable.effect = consumableType == ConsumableType.ENDURANCE ? effect * 3 : effect;
        consumable.price = calculatePrice(difficulty, effect, consumableType == ConsumableType.ENDURANCE ? 4 : 10);
        return consumable;
    }

    private static double calculatePrice(int difficulty, double effect, long ratio) {
        return BigDecimal.valueOf(difficulty * ratio + 5).multiply(BigDecimal.valueOf(effect))
                .setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    public static List<MarketPlayer> generatePlayers(GameInfo gameInfo) {
        int difficulty = gameInfo.difficulty.getNum();

        List<MarketPlayer> goods = new ArrayList<>();

        int playerCounts = ThreadLocalRandom.current().nextInt(3) + 3;
        PlayerRole[] values = PlayerRole.values();
        for (int i = 0; i < playerCounts; i++) {
            PlayerRole role = values[ThreadLocalRandom.current().nextInt(values.length)];
            MarketPlayer marketPlayer;
            if (gameInfo.generatorHighPlayer()) {
                if (gameInfo.generatorHigh2Player()) {
                    marketPlayer = generateMarketPlayer(difficulty + 2);
                } else {
                    marketPlayer = generateMarketPlayer(difficulty + 1);
                }
                gameInfo.generatorHighPlayer = true;
            } else {
                marketPlayer = generateMarketPlayer(difficulty);
            }
            marketPlayer.num = gameInfo.player_index.getAndIncrement();
            marketPlayer.name = "Number" + marketPlayer.num;
            marketPlayer.role = role;
            marketPlayer.description = role.description;
            goods.add(marketPlayer);
        }
        return goods;
    }

    public static List<MarketConsumable> generateConsumables(GameInfo gameInfo) {
        int difficulty = gameInfo.difficulty.getNum();

        List<MarketConsumable> goods = new ArrayList<>();
        int consumableCounts = ThreadLocalRandom.current().nextInt(4) + 3;
        ConsumableType[] consumableTypes = ConsumableType.values();
        for (int i = 0; i < consumableCounts; i++) {
            ConsumableType consumableType = consumableTypes[ThreadLocalRandom.current().nextInt(consumableTypes.length)];
            MarketConsumable consumable = generateMarketConsumable(difficulty, consumableType);
            consumable.num = gameInfo.consumable_index.getAndIncrement();
            goods.add(consumable);
        }
        return goods;
    }

    public static MarketConsumable generateFreeConsumable(GameInfo gameInfo, ConsumableType consumableType) {
        int difficulty = gameInfo.difficulty.getNum();

        double effect = getRandomDouble(RAISE_RANGE[difficulty][0], RAISE_RANGE[difficulty][1]);
        MarketConsumable consumable = new MarketConsumable();
        consumable.num = gameInfo.consumable_index.getAndIncrement();
        consumable.name = consumableType.name;
        consumable.type = consumableType;
        consumable.effect = consumableType == ConsumableType.ENDURANCE ? effect * 3 : effect;
        return consumable;
    }
}
