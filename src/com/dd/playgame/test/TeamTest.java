package com.dd.playgame.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dd.playgame.bean.Consumable;
import com.dd.playgame.bean.ConsumableType;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
    }

    @Test
    void testAddIntegral() {
        team.integral = 100;
        team.battleCount = 5;

        team.addIntegral(50);

        int expectedIntegral = 150;
        int expectedBattleCount = 6;

        assertEquals(expectedIntegral, team.integral);
        assertEquals(expectedBattleCount, team.battleCount);
    }

    @Test
    void testAddBonus() {
        team.amount = 100.0;
        team.winCount = 2;

        team.addBonus(50.0);

        double expectedAmount = 150.0;
        int expectedWinCount = 3;

        assertEquals(expectedAmount, team.amount);
        assertEquals(expectedWinCount, team.winCount);
    }

    @Test
    void testBuyConsumable() {
        MarketConsumable consumable = new MarketConsumable();
        consumable.price = 20.0;

        boolean success = team.buyConsumable(consumable);

        assertTrue(success);
        assertEquals(1, team.consumables.size());
        assertEquals(20.0, team.amount);
    }

    @Test
    void testBuyPlayer() {
        MarketPlayer player = new MarketPlayer();
        player.price = 50.0;
        player.role = PlayerRole.POWER_FORWARD;

        boolean success = team.buyPlayer(player, 1);

        assertTrue(success);
        assertEquals(1, team.players.size());
        assertEquals(50.0, team.amount);
    }

    @Test
    void testRefreshPlayerState() {
        Player player1 = new Player();
        Player player2 = new Player();
        team.players.add(player1);
        team.reserves.add(player2);

        player1.endurance = 80.0;
        player2.endurance = 90.0;

        team.refreshPlayerState();

        assertEquals(100.0, player1.endurance);
        assertEquals(100.0, player2.endurance);
    }

    @Test
    void testGetAmountStr() {
        team.amount = 123.456;

        String expected = "123.45";
        String actual = team.getAmountStr();

        assertEquals(expected, actual);
    }

    @Test
    void testUseConsumable_Endurance() {
        Player player = new Player();
        player.endurance = 80.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.ENDURANCE;
        consumable.effect = 20.0;

        String expected = "Successfully used, current endurance is 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.endurance);
        assertTrue(team.consumables.isEmpty());
    }

    @Test
    void testUseConsumable_Endurance_EnduranceReachedMax() {
        Player player = new Player();
        player.endurance = 100.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.ENDURANCE;
        consumable.effect = 20.0;

        String expected = "The endurance value has reached its maximum 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.endurance);
        assertTrue(team.consumables.isEmpty());
    }

    @Test
    void testUseConsumable_Strength() {
        Player player = new Player();
        player.strength = 80.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.STRENGTH;
        consumable.effect = 20.0;

        String expected = "Successfully used, current strength is 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.strength);
        assertTrue(team.consumables.isEmpty());
    }

    @Test
    void testUseConsumable_Strength_StrengthReachedMax() {
        Player player = new Player();
        player.strength = 100.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.STRENGTH;
        consumable.effect = 20.0;

        String expected = "The strength value has reached its maximum 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.strength);
        assertTrue(team.consumables.isEmpty());
    }

    @Test
    void testUseConsumable_Defense() {
        Player player = new Player();
        player.defense = 80.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.DEFENSE;
        consumable.effect = 20.0;

        String expected = "Successfully used, current defense is 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.defense);
        assertTrue(team.consumables.isEmpty());
    }

    @Test
    void testUseConsumable_Defense_DefenseReachedMax() {
        Player player = new Player();
        player.defense = 100.0;
        Consumable consumable = new Consumable();
        consumable.type = ConsumableType.DEFENSE;
        consumable.effect = 20.0;

        String expected = "The defense value has reached its maximum 100.00";
        String actual = team.useConsumable(player, consumable);

        assertEquals(expected, actual);
        assertEquals(100.0, player.defense);
        assertTrue(team.consumables.isEmpty());
    }


    @Test
    void testCalculateTeamScore() {
        Player player1 = new Player();
        player1.strength = 80.0;
        player1.defense = 70.0;
        player1.endurance = 90.0;
        team.players.add(player1);

        Player player2 = new Player();
        player2.strength = 90.0;
        player2.defense = 75.0;
        player2.endurance = 85.0;
        team.players.add(player2);

        BigDecimal expectedScore = BigDecimal.valueOf(310.0).setScale(2);
        BigDecimal actualScore = team.calculateTeamScore();

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void testTeamScoreStr() {
        BigDecimal teamScore = BigDecimal.valueOf(310.0);

        team.integral = 100;
        team.winCount = 5;

        String expected = "310.00";
        String actual = team.teamScoreStr();

        assertEquals(expected, actual);
    }

    @Test
    void testFormatBasic() {
        team.name = "Team A";
        BigDecimal teamScore = BigDecimal.valueOf(310.0);
        team.integral = 100;
        team.winCount = 5;

        String expected = "Team: Team A                 Player Comprehensive score:     310.00";
        String actual = team.formatBasic();

        assertEquals(expected, actual);
    }
}

