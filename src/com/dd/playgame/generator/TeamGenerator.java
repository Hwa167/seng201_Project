package com.dd.playgame.generator;

import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;
import com.dd.playgame.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate System Level Teams
 */
public class TeamGenerator {

    /**
     * Team Name
     */
    private static final String[] teamNames = {"Lions", "Tigers", "Bears", "Eagles", "Wolves", "Panthers", "Sharks", "Dragons", "Phoenix", "Thunder"};

    /**
     * Generate Team on game difficulty
     *
     * @param gameInfo {@link  GameInfo}
     * @return {@link Player}
     */
    public static Team generateTeam(GameInfo gameInfo) {
        int difficulty = gameInfo.difficulty.getNum();

        Team team = new Team();
        team.name = getRandomName();

        List<String> useNames = new ArrayList<>();

        List<Player> players = new ArrayList<>();
        for (PlayerRole value : PlayerRole.values()) {
            Player player = PlayerGenerator.generatePlayer(difficulty);
            while (useNames.contains(player.name)) {
                player.name = PlayerGenerator.getRandomName();
            }
            useNames.add(player.name);

            player.num = gameInfo.player_index.getAndIncrement();
            player.role = value;
            players.add(player);
        }
        team.players = players;

        return team;
    }

    /**
     * Generate 3-5 teams
     *
     * @param gameInfo game info
     * @return teams
     */
    public static List<Team> generateTeams(GameInfo gameInfo) {
        List<String> useTeamNames = new ArrayList<>();

        List<Team> teams = new ArrayList<>();
        int count = RandomUtils.getRandomInt(3, 5);
        for (int i = 0; i < count; i++) {
            Team team = generateTeam(gameInfo);
            if (useTeamNames.contains(team.name)) {
                while (useTeamNames.contains(team.name)) {
                    team.name = getRandomName();
                }
            }
            useTeamNames.add(team.name);
            teams.add(team);
        }

        useTeamNames.clear();

        return teams;
    }

    /**
     * Randomly select a name from an array
     *
     * @return name
     */
    public static String getRandomName() {
        return teamNames[RandomUtils.getRandomInt(teamNames.length - 1)];
    }
}
