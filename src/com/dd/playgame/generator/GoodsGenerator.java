package com.dd.playgame.generator;

import com.dd.playgame.bean.*;
import com.dd.playgame.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The market is responsible for generate athletes and consumables
 */
public class GoodsGenerator {

    /**
     * Generate 3-5 athletes for sale
     *
     * @param gameInfo game info
     * @param market   market
     */
    public static void generatePlayers(GameInfo gameInfo, Market market) {
        int difficulty = gameInfo.difficulty.getNum();

        List<MarketPlayer> goods = new ArrayList<>();

        int playerCounts = RandomUtils.getRandomInt(3, 5);
        PlayerRole[] values = PlayerRole.values();
        for (int i = 0; i < playerCounts; i++) {
            PlayerRole role = values[RandomUtils.getRandomInt(values.length - 1)];
            MarketPlayer marketPlayer;
            if (RandomEventGenerator.generateHighPlayerProb(difficulty)) {
                if (RandomEventGenerator.generateHigh2PlayerProb(difficulty)) {
                    marketPlayer = PlayerGenerator.generateMarketPlayer(difficulty + 2);
                } else {
                    marketPlayer = PlayerGenerator.generateMarketPlayer(difficulty + 1);
                }
                market.setHaveHighPlayer(true);
            } else {
                marketPlayer = PlayerGenerator.generateMarketPlayer(difficulty);
            }
            marketPlayer.num = gameInfo.player_index.getAndIncrement();
            marketPlayer.name = "Number" + marketPlayer.num;
            marketPlayer.role = role;
            marketPlayer.description = role.description;
            goods.add(marketPlayer);
        }
        market.players.addAll(goods);
    }

    /**
     * Generate 3-6 consumable for sale
     *
     * @param gameInfo game info
     * @param market   market
     */
    public static void generateConsumables(GameInfo gameInfo, Market market) {
        int difficulty = gameInfo.difficulty.getNum();

        List<MarketConsumable> goods = new ArrayList<>();
        int consumableCounts = RandomUtils.getRandomInt(3, 5);
        ConsumableType[] consumableTypes = ConsumableType.values();
        for (int i = 0; i < consumableCounts; i++) {
            ConsumableType consumableType = consumableTypes[RandomUtils.getRandomInt(consumableTypes.length - 1)];
            MarketConsumable consumable = ConsumableGenerator.generateMarketConsumable(difficulty, consumableType);
            consumable.num = gameInfo.consumable_index.getAndIncrement();
            goods.add(consumable);
        }
        market.consumables.addAll(goods);
    }

}
