package com.dd.playgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketBallConfig {

    public static final double INIT_AMOUNT =1000d;

    public static final double BONUS = 100d;

    public static final int GAME_WIN = 80;

    public static final int GAME_LOSE = 10;

    public static final int GAME_DRAW = 5;

    public static final double ENDURANCE_DECLINE = 50d;

    public static final Map<Integer, Double> ENDURANCE_DECLINE_RATIO = new HashMap<Integer, Double>();
    static {
        ENDURANCE_DECLINE_RATIO.put(-2,0.8);
        ENDURANCE_DECLINE_RATIO.put(-1,0.7);
        ENDURANCE_DECLINE_RATIO.put(0,0.5);
        ENDURANCE_DECLINE_RATIO.put(1,0.3);
        ENDURANCE_DECLINE_RATIO.put(2,0.2);
    }

    public static double calculateDecline(int score){
        return ENDURANCE_DECLINE * ENDURANCE_DECLINE_RATIO.get(score);
    }


    public static final int ENDURANCE_MAX = 100;
}
