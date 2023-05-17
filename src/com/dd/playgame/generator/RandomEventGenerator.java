package com.dd.playgame.generator;

import com.dd.playgame.util.RandomUtils;

/**
 * Probability of generating random events
 */
public class RandomEventGenerator {

    /**
     * Probability of improving athletes' abilities
     *
     * @param difficulty Game difficulty
     * @return with or without
     */
    public static boolean playerIncreaseProb(int difficulty) {
        int max = 10;
        return RandomUtils.getRandomInt(1, max) == 1;
    }


    /**
     * The probability of athletes giving up the competition
     *
     * @param difficulty Game difficulty
     * @return with or without
     */
    public static boolean playerAbandonProb(int difficulty) {
        int max = 100;
        return RandomUtils.getRandomInt(1, max) == 1;
    }

    /**
     * Probability of generate higher athletes
     *
     * @param difficulty Game difficulty
     * @return with or without
     */
    public static boolean generateHighPlayerProb(int difficulty) {
        int max = 10;
        return RandomUtils.getRandomInt(1, max) == 1;
    }

    /**
     * Probability of generate higher level athletes.<br>
     * Built on the foundation of {@link #generateHighPlayerProb(int)}
     *
     * @param difficulty Game difficulty
     * @return with or without
     */
    public static boolean generateHigh2PlayerProb(int difficulty) {
        return RandomUtils.getRandomInt(1, 20) == 1;
    }
}
