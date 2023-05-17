package com.dd.playgame.test;

import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;
import com.dd.playgame.command.Battle;
import com.dd.playgame.generator.TeamGenerator;
import com.dd.playgame.util.BattlePromptUtils;
import com.dd.playgame.util.RandomUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    private Team userTeam;
    private Team systemTeam;
    private boolean isMatch;

    @BeforeEach
    void setup() {
        // Create user teams and system teams for testing
        userTeam = new Team();
        userTeam.name = "UserTeam";
        Player player1 = new Player();
        player1.name = "Player1";
        player1.role = PlayerRole.CENTER;
        player1.endurance = 100.0;
        player1.strength = 80.0;
        player1.defense = 70.0;
        Player player2 = new Player();
        player2.name = "Player2";
        player2.role = PlayerRole.POINT_GUARD;
        player2.endurance = 100.0;
        player2.strength = 70.0;
        player2.defense = 80.0;
        userTeam.players = Arrays.asList(player1, player2);

        systemTeam = new Team();
        systemTeam.name = "SystemTeam";
        Player systemPlayer1 = new Player();
        systemPlayer1.name = "SystemPlayer1";
        systemPlayer1.role = PlayerRole.CENTER;
        systemPlayer1.endurance = 100.0;
        systemPlayer1.strength = 75.0;
        systemPlayer1.defense = 65.0;
        Player systemPlayer2 = new Player();
        systemPlayer2.name = "SystemPlayer2";
        systemPlayer2.role = PlayerRole.POINT_GUARD;
        systemPlayer2.endurance = 100.0;
        systemPlayer2.strength = 65.0;
        systemPlayer2.defense = 75.0;
        systemTeam.players = Arrays.asList(systemPlayer1, systemPlayer2);

        isMatch = false;
    }

    @Test
    void testBattleTeam_InvalidUserTeam() {
        // lack some people
        userTeam.players = new ArrayList<>();
        boolean result = Battle.battleTeam(userTeam, systemTeam, isMatch);
        assertFalse(result);
    }

    @Test
    void testBattleTeam_InsufficientEndurance() {
        // lack endurance
        userTeam.players.get(0).endurance = 0.0;
        boolean result = Battle.battleTeam(userTeam, systemTeam, isMatch);
        assertFalse(result);
    }

    @Test
    void testBattleTeam_NoSystemTeam() {
        // no system team
        boolean result = Battle.battleTeam(userTeam, null, isMatch);
        assertFalse(result);
    }

    @Test
    void testBattleTeam_NoMatch() {
        // Test non-match mode
        boolean result = Battle.battleTeam(userTeam, systemTeam, isMatch);
        assertTrue(result);
    }


    @Test
    void testBattlePlayer() throws InterruptedException {
        // For ease of testing, redirect standard output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Player leftPlayer = userTeam.players.get(0);
        Player rightPlayer = systemTeam.players.get(0);
        Double[] scores = Battle.battlePlayer(leftPlayer, rightPlayer);

        System.setOut(System.out);

        assertNotNull(scores);
        assertEquals(2, scores.length);
    }

    @Test
    void testBattleCapacity_LeftGreater() {
        int result = Battle.battleCapacity(10.0, 5.0);
        assertEquals(1, result);
    }

    @Test
    void testBattleCapacity_RightGreater() {
        int result = Battle.battleCapacity(5.0, 10.0);
        assertEquals(-1, result);
    }

    @Test
    void testBattleCapacity_Equal() {
        int result = Battle.battleCapacity(5.0, 5.0);
        assertEquals(0, result);
    }

    @Test
    void testBattleCapacity_LeftGreaterWithDecimals() {
        int result = Battle.battleCapacity(5.5, 5.2);
        assertEquals(1, result);
    }

    @Test
    void testBattleCapacity_RightGreaterWithDecimals() {
        int result = Battle.battleCapacity(5.2, 5.5);
        assertEquals(-1, result);
    }

    @Test
    void testBattleCapacity_EqualWithDecimals() {
        int result = Battle.battleCapacity(5.2, 5.2);
        assertEquals(0, result);
    }
}

