package com.dd.playgame.test;

import com.dd.playgame.bean.Difficulty;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;
import com.dd.playgame.generator.TeamGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TeamGeneratorTest {

    private GameInfo gameInfo;

    @BeforeEach
    public void setUp() {
        // Set up the GameInfo object with the necessary values for testing
        gameInfo = new GameInfo();
        gameInfo.difficulty = Difficulty.NORMAL; // Set the desired difficulty level
        gameInfo.player_index = new AtomicInteger(1); // Set the initial player index
    }

    @Test
    public void testGenerateTeam() {
        // Generate a team
        Team team = TeamGenerator.generateTeam(gameInfo);

        // Verify that the team is not null
        Assertions.assertNotNull(team);

        // Verify that the team name is not empty
        Assertions.assertFalse(team.name.isEmpty());

        // Verify that the team has the correct number of players
        Assertions.assertEquals(PlayerRole.values().length, team.players.size());

        // Verify that each player has a unique name and number
        Set<String> playerNames = new HashSet<>();
        Set<Integer> playerNumbers = new HashSet<>();

        for (Player player : team.players) {
            Assertions.assertFalse(playerNames.contains(player.name), "Duplicate player name: " + player.name);
            Assertions.assertFalse(playerNumbers.contains(player.num), "Duplicate player number: " + player.num);
            playerNames.add(player.name);
            playerNumbers.add(player.num);
        }
    }

    @Test
    public void testGenerateTeams() {
        // Generate teams
        List<Team> teams = TeamGenerator.generateTeams(gameInfo);

        // Verify that the list of teams is not null
        Assertions.assertNotNull(teams);

        // Verify that the number of generated teams is within the expected range
        Assertions.assertTrue(teams.size() >= 3 && teams.size() <= 5);

        // Verify that each team has a unique name
        Set<String> teamNames = new HashSet<>();

        for (Team team : teams) {
            Assertions.assertFalse(teamNames.contains(team.name), "Duplicate team name: " + team.name);
            teamNames.add(team.name);
        }
    }

    @Test
    public void testGetRandomName() {
        // Call getRandomName multiple times and verify that the returned names are valid
        for (int i = 0; i < 100; i++) {
            String name = TeamGenerator.getRandomName();
            Assertions.assertNotNull(name);
            Assertions.assertTrue(name.length() > 0);
        }
    }
}

