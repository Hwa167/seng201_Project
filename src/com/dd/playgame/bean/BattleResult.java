package com.dd.playgame.bean;

import com.dd.playgame.application.Difficulty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class BattleResult {

    private double team1Score;
    private double team2Score;

    private final boolean isTeam1Win;

    private final int team1Points;

    private final int team2Points;

    private final double team1Money;

    private final double team2Money;

    public BattleResult(double team1Score, double team2Score, Difficulty difficulty) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;

        BigDecimal bigDecimal1 = BigDecimal.valueOf(team1Score);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(team2Score);

        this.isTeam1Win = bigDecimal1.compareTo(bigDecimal2) == 0
                ? ThreadLocalRandom.current().nextBoolean()
                : team1Score > team2Score;

        double scoreDiff = Math.abs(team1Score - team2Score);

        double money = 0;
        int points = 0;

        switch (difficulty) {
            case HARD:
                money = 100;
                points = 300;
                break;
            case NORMAL:
                money = 150;
                points = 200;
                break;
            case EASY:
                money = 200;
                points = 100;
                break;
        }

        if (scoreDiff <= 10) {
            points *= 2;
            money /= 2;
        } else if (scoreDiff <= 20) {
            points *= 1.5;
            money *= 0.8;
        }

        if (isTeam1Win) {
            team1Points = points;
            team1Money = money;

            team2Points = points / 2;
            team2Money = money / 2;
        } else {
            team2Points = points;
            team2Money = money;

            team1Points = points / 2;
            team1Money = money / 2;
        }
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
    }

    public double getTeam1Money() {
        return team1Money;
    }

    public double getTeam2Money() {
        return team2Money;
    }

    public boolean isTeam1Win() {
        return isTeam1Win;
    }

    public String getTeam1MoneyStr() {
        return BigDecimal.valueOf(team1Money).setScale(2, RoundingMode.FLOOR).toPlainString();
    }
}
