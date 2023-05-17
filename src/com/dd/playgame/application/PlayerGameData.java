package com.dd.playgame.application;

import com.dd.playgame.bean.BattleResult;
import com.dd.playgame.bean.Market;
import com.dd.playgame.bean.Team;

public class PlayerGameData {

    public static final double INIT_AMOUNT = 1000d;

    private static GameInfo gameInfo;

    static {
        initConfig(Difficulty.EASY, "Sky", 10);
    }

    public static void nextWeek() {
        gameInfo.nextWeek();
    }

    public static GameInfo getGameInfo() {
        return gameInfo;
    }

    public static void setGameInfo(GameInfo gameInfo) {
        PlayerGameData.gameInfo = gameInfo;
    }

    public static void initConfig(Difficulty difficulty, String teamName, int cycle) {
        DataHandler.deleteData();

        GameInfo initGameInfo = new GameInfo();
        initGameInfo.difficulty = difficulty;
        initGameInfo.allCycle = cycle;
        initGameInfo.cycle = 1;

        Team userTeam = new Team();
        userTeam.name = teamName;
        userTeam.initAmount = INIT_AMOUNT;
        userTeam.amount = INIT_AMOUNT;
        initGameInfo.team = userTeam;

        Market market = new Market();
        market.refresh(initGameInfo);
        initGameInfo.market = market;

        setGameInfo(initGameInfo);
    }

    public static BattleResult matchSettlement(double team1Score, double team2Score) {
        Difficulty difficulty = gameInfo.difficulty;

        BattleResult battleResult = new BattleResult(team1Score, team2Score, difficulty);
        if (battleResult.isTeam1Win()) {
            gameInfo.team.addBonus(battleResult.getTeam1Money());
        }
        gameInfo.team.addIntegral(battleResult.getTeam1Points());

        return battleResult;
    }
}
