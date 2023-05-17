package com.dd.playgame.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    /**
     * Get random integer number from 0 to maximum.
     *
     * @param max maxNum
     * @return number
     */
    public static int getRandomInt(int max) {
        return getRandomInt(0, max);
    }

    /**
     * Get random integer number from minimum to maximum.
     *
     * @param min minNum
     * @param max maxNum
     * @return number
     */
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Get random double number from minimum to maximum.
     *
     * @param min minNum
     * @param max maxNum
     * @return number
     */
    public static double getRandomDouble(double min, double max) {
        double randomDouble = ThreadLocalRandom.current().nextDouble(min, max + 0.01);
        return BigDecimal.valueOf(randomDouble)
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    /**
     * Get random boolean
     *
     * @return true or false
     */
    public static boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
