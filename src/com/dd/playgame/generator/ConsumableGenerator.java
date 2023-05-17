package com.dd.playgame.generator;

import com.dd.playgame.bean.Consumable;
import com.dd.playgame.bean.ConsumableType;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.util.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Responsible for generating consumable
 */
public class ConsumableGenerator {

    /**
     * Range of athlete's ability improvement
     */
    private static final double[][] RAISE_RANGE = {
            {2d, 3d},   // easy
            {1d, 2d},  // norm
            {0.2, 1d}   // hard
    };

    /**
     * Generate consumables based on game difficulty and type of consumables
     *
     * @param difficulty     difficulty
     * @param consumableType {@link  ConsumableType}
     * @return consumable
     */
    public static MarketConsumable generateMarketConsumable(int difficulty, ConsumableType consumableType) {
        double effect = RandomUtils.getRandomDouble(RAISE_RANGE[difficulty][0], RAISE_RANGE[difficulty][1]);
        MarketConsumable consumable = new MarketConsumable();
        consumable.name = consumableType.name;
        consumable.type = consumableType;
        consumable.description = consumableType.description;
        consumable.effect = consumableType == ConsumableType.ENDURANCE ? effect * 3 : effect;
        consumable.price = calculatePrice(difficulty, effect, consumableType);
        return consumable;
    }

    /**
     * Calculate the selling price of consumables based on the difficulty of the game
     *
     * @param difficulty     difficulty
     * @param effect         Capability Impact Value
     * @param consumableType {@link  ConsumableType}
     * @return price
     */
    private static double calculatePrice(int difficulty, double effect, ConsumableType consumableType) {
        long ratio = consumableType == ConsumableType.ENDURANCE ? 4 : 10;
        return BigDecimal.valueOf(difficulty * ratio + 5).multiply(BigDecimal.valueOf(effect))
                .setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * When an ability improvement event occurs,
     * generate a free consumable bottle based on the difficulty level
     *
     * @param gameInfo       game info
     * @param consumableType consumable type
     * @return consumable
     */
    public static Consumable generateFreeConsumable(GameInfo gameInfo, ConsumableType consumableType) {
        int difficulty = gameInfo.difficulty.getNum();

        double effect = RandomUtils.getRandomDouble(RAISE_RANGE[difficulty][0], RAISE_RANGE[difficulty][1]);
        Consumable consumable = new Consumable();
        consumable.num = gameInfo.consumable_index.getAndIncrement();
        consumable.name = consumableType.name;
        consumable.type = consumableType;
        consumable.effect = consumableType == ConsumableType.ENDURANCE ? effect * 3 : effect;
        return consumable;
    }
}
