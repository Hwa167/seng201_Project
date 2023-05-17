package com.dd.playgame.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.PlayerRole;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MarketPlayerTest {

    private MarketPlayer marketPlayer;

    @BeforeEach
    void setUp() {
        marketPlayer = new MarketPlayer();
    }

    @Test
    void testGetPriceStr() {
        marketPlayer.price = 10.0;

        String expected = "10.00";
        String actual = marketPlayer.getPriceStr();

        assertEquals(expected, actual);
    }

    @Test
    void testGetSellPrice() {
        marketPlayer.price = 10.0;

        double expected = 8.0;
        double actual = marketPlayer.getSellPrice();

        assertEquals(expected, actual);
    }

    @Test
    void testGetSellPriceStr() {
        marketPlayer.price = 10.0;

        String expected = "8.00";
        String actual = marketPlayer.getSellPriceStr();

        assertEquals(expected, actual);
    }

    @Test
    void testFormat() {
        marketPlayer.name = "Player 1";
        marketPlayer.price = 10.0;
        marketPlayer.addStrength(80);
        marketPlayer.addDefense(70);

        String expected = "Player      Player 1      strength:80   defense:70   role:FORWARD               price:10.00  ";
        String actual = marketPlayer.format();

        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        marketPlayer.price = 10.0;
        marketPlayer.num = 1;
        marketPlayer.name = "Player 1";
        marketPlayer.addStrength(80);
        marketPlayer.addDefense(70);
        marketPlayer.addEndurance(90);
        marketPlayer.description = "Description";

        String expected = "MarketPlayer{price=10.0, num=1, name='Player 1', strength=80, defense=70, " +
                "endurance=90, role=FORWARD, description='Description'}";
        String actual = marketPlayer.toString();

        assertEquals(expected, actual);
    }
}

