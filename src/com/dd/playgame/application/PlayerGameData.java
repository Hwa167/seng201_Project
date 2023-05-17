package com.dd.playgame.application;

import com.dd.playgame.bean.*;
import com.dd.playgame.gui.MessageFrame;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Basic game data, used for serialization to complete archiving functions
 */
public class PlayerGameData {

    /**
     * Game initial money
     */
    private static final double INIT_AMOUNT = 1000d;

    private static GameInfo gameInfo;

    static {
        initConfig(Difficulty.EASY, "Sky", 10);
    }

    /**
     * to next week
     */
    public static void nextWeek() {
        gameInfo.nextWeek();
    }

    /**
     * Check if this week is the last week
     *
     * @return boolean
     */
    public static boolean isLastWeek() {
        return gameInfo.isLastWeek();
    }

    public static GameInfo getGameInfo() {
        return gameInfo;
    }

    public static void setGameInfo(GameInfo gameInfo) {
        PlayerGameData.gameInfo = gameInfo;
    }

    /**
     * Initialize game data
     *
     * @param difficulty Selected Game Difficulty
     * @param teamName   Entered team name
     * @param cycle      Duration period
     */
    public static void initConfig(Difficulty difficulty, String teamName, int cycle) {
        DataHandler.deleteData();

        GameInfo initGameInfo = new GameInfo();
        initGameInfo.difficulty = difficulty;
        initGameInfo.allCycle = cycle;
        initGameInfo.cycle = 1;

        Team userTeam = new Team();
        userTeam.name = teamName;
        userTeam.initAmount = INIT_AMOUNT;
        userTeam.amount = userTeam.initAmount;
        initGameInfo.team = userTeam;

        Market market = new Market();
        market.refresh(initGameInfo);
        initGameInfo.market = market;

        setGameInfo(initGameInfo);
    }

    /**
     * Calculate bonus and points based on the total score of both teams
     *
     * @param team1Score user team
     * @param team2Score system team
     * @return {@link BattleResult}
     */
    public static BattleResult matchSettlement(double team1Score, double team2Score, boolean isMatch) {
        Difficulty difficulty = gameInfo.difficulty;

        BattleResult battleResult = new BattleResult(team1Score, team2Score, difficulty);
        if (battleResult.isTeam1Win()) {
            gameInfo.team.addBonus(battleResult.getTeam1Money());
            gameInfo.team.addIntegral(battleResult.getTeam1Points());
        }else if(isMatch){
            gameInfo.team.addIntegral(battleResult.getTeam1Points());
        }

        return battleResult;
    }

    public static boolean canBattle(JButton button){
        //Check the team for compliance with the rules
        ArrayList<String> absentRoles = new ArrayList<>();
        for (PlayerRole role : PlayerRole.values()) {
            boolean exists = gameInfo.team.players.stream().anyMatch(item -> item.role == role);
            if (!exists) {
                absentRoles.add(role.value);
            }
        }
        if (!absentRoles.isEmpty()) {
            new MessageFrame("Tips", "There is a shortage of athletes with roles "+
                    absentRoles
                    +" in the team, go to the market and take a look!", true, button);
            return false;
        }

        boolean checkEndurance = gameInfo.team.players.stream().filter(item -> item.endurance > 0).count() == 5;
        if (!checkEndurance) {
            new MessageFrame("Tips", "Some members of the team have insufficient endurance and cannot participate in the competition!", true, button);
            return false;
        }
        return true;
    }
}
