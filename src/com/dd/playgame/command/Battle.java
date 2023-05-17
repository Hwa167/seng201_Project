package com.dd.playgame.command;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.BattleResult;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;
import com.dd.playgame.generator.TeamGenerator;
import com.dd.playgame.util.BattlePromptUtils;
import com.dd.playgame.util.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Temporary creation, Command line program usage
 */
public class Battle {
	/**
     * Perform a battle between userTeam and systemTeam
     *
     * @param userTeam    User's team
     * @param systemTeam  System's team
     * @param isMatch     Indicates if it's a match or a training battle
     * @return True if the battle was conducted successfully, False otherwise
     */
    public static boolean battleTeam(Team userTeam, Team systemTeam, boolean isMatch) {
        //Check the team for compliance with the rules
        ArrayList<PlayerRole> absentRoles = new ArrayList<>();
        for (PlayerRole value : PlayerRole.values()) {
            boolean exists = userTeam.players.stream().anyMatch(item -> item.role == value);
            if (!exists) {
                absentRoles.add(value);
            }
        }
        if (!absentRoles.isEmpty()) {
            System.out.println(userTeam.name + " Lack Type" + absentRoles + "please, go to shop!");
            return false;
        }
        // Check if userTeam has 5 players with positive endurance
        boolean checkEndurance = userTeam.players.stream().filter(item -> item.endurance > 0).count() == 5;
        if (!checkEndurance) {
            System.out.println(userTeam.name + " Cannot Match!");
            return false;
        }
        // Generate a new systemTeam if it's a match
        if (isMatch) {
            systemTeam = TeamGenerator.generateTeam(PlayerGameData.getGameInfo());
        }

        System.out.println("Match Start");

        List<Double[]> scores = new ArrayList<>();
        
        // Create a list of battleRoles to be processed
        ArrayList<PlayerRole> battleRoles = new ArrayList<>(Arrays.asList(PlayerRole.values()));
        // Process each battleRole
        while (!battleRoles.isEmpty()) {
            PlayerRole battleRole = battleRoles.get(RandomUtils.getRandomInt(battleRoles.size() - 1));
            battleRoles.remove(battleRole);

            Optional<Player> systemPlayer = systemTeam.players.stream().filter(item -> item.role == battleRole).findFirst();
            Optional<Player> userPlayer = userTeam.players.stream().filter(item -> item.role == battleRole).findFirst();

            Double[] itemScores = new Double[0];
            try {
                itemScores = battlePlayer(userPlayer.get(), systemPlayer.get());
            } catch (InterruptedException ignored) {
            }
            scores.add(itemScores);
        }
        double leftAllScore = 0d, rightAllScore = 0d;
        for (Double[] score : scores) {
            leftAllScore += score[0];
            rightAllScore += score[1];
        }
        System.out.println("At the end of the competition, Team " + userTeam.name + " had a total score of " + leftAllScore + " and Team " + systemTeam.name + " had a total score of " + rightAllScore + ".");
        BattleResult result = PlayerGameData.matchSettlement(leftAllScore, rightAllScore, isMatch);
        if (result.isTeam1Win()) {
            System.out.println("Congratulations on winning this game, you have earned a bonus of " + result.getTeam1MoneyStr() + " and " + result.getTeam1Points() + " points. Keep up the good work!");
        } else {
            if (isMatch) {
                System.out.println("You lost this game and earned " + result.getTeam1Points() + " points, keep it up and try again next time...");
            } else {
                System.out.println("You lost this game, keep it up and try again next time...");
            }
        }

        return true;
    }
    
    /**
     * Battle two players and obtain the scores
     *
     * @param leftPlayer  The left player
     * @param rightPlayer The right player
     * @return An array of scores [leftScore, rightScore]
     * @throws InterruptedException If the thread is interrupted during sleep
     */
    public static Double[] battlePlayer(Player leftPlayer, Player rightPlayer) throws InterruptedException {
        //Create confrontation and increase fatigue
        System.out.println(leftPlayer.name + " and " + rightPlayer.name + " are competing in strength...");
        Thread.sleep(200L);
        double score1 = leftPlayer.calculateScore();
        double score2 = rightPlayer.calculateScore();

        //At the same score, randomly obtain one side as the winning side
        boolean win = score1 == score2 ? RandomUtils.getRandomBoolean() : score1 > score2;
        System.out.println(BattlePromptUtils.getGreeting(win));
        Thread.sleep(200L);

        System.out.println(leftPlayer.name + " capability score of " + score1 + ", "
                + rightPlayer.name + " capability score of " + score2 + ".  "
                + (win ? leftPlayer.name : rightPlayer.name) + " wins.");
        Thread.sleep(200L);

        System.out.println("------------------------------------------------------------------");
        double basic = 3.0;

        //Calculate the endurance value to be reduced
        double scoreRatio = score1 / (score1 + score2);
        double endurance1 = basic + scoreRatio * 10;
        double endurance2 = basic + (1 - scoreRatio) * 10;
        if (win) {
            leftPlayer.declineEndurance(endurance2);
        } else {
            leftPlayer.declineEndurance(endurance1);
        }
        return new Double[]{score1, score2};
    }
    /**
     * Compare the battle capacity of two values
     *
     * @param left  The left value
     * @param right The right value
     * @return A negative value if left < right, 0 if left == right, a positive value if left > right
     */
    public static int battleCapacity(double left, double right) {
        return Double.compare(left, right);
    }
}
