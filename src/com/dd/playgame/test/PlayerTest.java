package com.dd.playgame.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testCalculateScore() {
        player.strength = 80.0;
        player.defense = 70.0;
        player.endurance = 90.0;

        double expected = 155.0;
        double actual = player.calculateScore();

        assertEquals(expected, actual);
    }

    @Test
    void testRefreshEndurance() {
        player.endurance = 80.0;
        player.refreshEndurance();

        double expected = 100.0;
        double actual = player.endurance;

        assertEquals(expected, actual);
    }

    @Test
    void testDeclineEndurance() {
        player.endurance = 80.0;
        player.declineEndurance(20.0);

        double expected = 60.0;
        double actual = player.endurance;

        assertEquals(expected, actual);
    }

    @Test
    void testDeclineEndurance_EnduranceCannotBeNegative() {
        player.endurance = 10.0;
        player.declineEndurance(20.0);

        double expected = 0.0;
        double actual = player.endurance;

        assertEquals(expected, actual);
    }

    @Test
    void testAddEndurance() {
        player.endurance = 80.0;
        player.addEndurance(20.0);

        double expected = 100.0;
        double actual = player.endurance;

        assertEquals(expected, actual);
    }

    @Test
    void testAddEndurance_EnduranceCannotExceedMaxValue() {
        player.endurance = 90.0;
        player.addEndurance(20.0);

        double expected = 100.0;
        double actual = player.endurance;

        assertEquals(expected, actual);
    }

    @Test
    void testAddDefense() {
        player.defense = 80.0;
        player.addDefense(10.0);

        double expected = 90.0;
        double actual = player.defense;

        assertEquals(expected, actual);
    }

    @Test
    void testAddDefense_DefenseCannotExceedMaxValue() {
        player.defense = 95.0;
        player.addDefense(10.0);

        double expected = 100.0;
        double actual = player.defense;

        assertEquals(expected, actual);
    }

    @Test
    void testAddStrength() {
        player.strength = 80.0;
        player.addStrength(10.0);

        double expected = 90.0;
        double actual = player.strength;

        assertEquals(expected, actual);
    }

    @Test
    void testAddStrength_StrengthCannotExceedMaxValue() {
        player.strength = 95.0;
        player.addStrength(10.0);

        double expected = 100.0;
        double actual = player.strength;

        assertEquals(expected, actual);
    }

    @Test
    void testGetStrengthStr() {
        player.strength = 80.0;

        String expected = "80.00";
        String actual = player.getStrengthStr();

        assertEquals(expected, actual);
    }

    @Test
    void testGetDefenseStr() {
        player.defense = 70.0;

        String expected = "70.00";
        String actual = player.getDefenseStr();

        assertEquals(expected, actual);
    }

    @Test
    void testGetEnduranceStr() {
        player.endurance = 90.0;

        String expected = "90.00";
        String actual = player.getEnduranceStr();

        assertEquals(expected, actual);
    }

    @Test
    void testFormatBasic() {
        player.name = "Player 1";
        player.strength = 80.0;
        player.defense = 70.0;
        player.endurance = 90.0;
        player.role = PlayerRole.POWER_FORWARD;

        String expected = "Player 1     strength:80   defense:70   endurance:90   role:FORWARD               ";
        String actual = player.formatBasic();

        assertEquals(expected, actual);
    }
}
