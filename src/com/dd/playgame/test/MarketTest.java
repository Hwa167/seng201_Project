package com.dd.playgame.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.Market;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.Team;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    private Market market;

    @BeforeEach
    void setUp() {
        market = new Market();
    }


    @Test
    void testRefreshGoods() {
        List<MarketPlayer> players = new ArrayList<>();
        MarketPlayer player1 = new MarketPlayer();
        player1.name = "Player 1";
        players.add(player1);

        List<MarketConsumable> consumables = new ArrayList<>();
        MarketConsumable consumable1 = new MarketConsumable();
        consumable1.name = "Consumable 1";
        consumables.add(consumable1);

        market.players = players;
        market.consumables = consumables;

    }

    @Test
    void testRemoveGoods() {
        MarketPlayer player1 = new MarketPlayer();
        player1.name = "Player 1";

        MarketPlayer player2 = new MarketPlayer();
        player2.name = "Player 2";

        market.players = new ArrayList<>();
        market.players.add(player1);
        market.players.add(player2);

        assertEquals(2, market.players.size());

    }

    @Test
    void testBuyGoods_Consumable_SuccessfulPurchase() {
        MarketConsumable consumable = new MarketConsumable();
        consumable.price = 10.0;

        GameInfo gameInfo = new GameInfo();
        Team team = new Team();
        team.amount = 20.0;
        gameInfo.team = team;

        PlayerGameData.setGameInfo(gameInfo);

        String expected = "Successfully purchased, current balance 10.00";
        String actual = market.buyGoods(consumable);

        assertEquals(expected, actual);
        assertTrue(gameInfo.team.consumables.contains(consumable));
        assertFalse(market.consumables.contains(consumable));
    }

    @Test
    void testBuyGoods_Consumable_InsufficientFunds() {
        MarketConsumable consumable = new MarketConsumable();
        consumable.price = 10.0;

        GameInfo gameInfo = new GameInfo();
        Team team = new Team();
        team.amount = 5.0;
        gameInfo.team = team;

        PlayerGameData.setGameInfo(gameInfo);

        String expected = "Sorry, your credit is running low!";
        String actual = market.buyGoods(consumable);

        assertEquals(expected, actual);
        assertFalse(gameInfo.team.consumables.contains(consumable));
        assertTrue(market.consumables.contains(consumable));
    }

    @Test
    void testBuyGoods_Player_SuccessfulPurchase() {
        MarketPlayer player = new MarketPlayer();
        player.price = 100.0;

        GameInfo gameInfo = new GameInfo();
        Team team = new Team();
        team.amount = 200.0;
        gameInfo.team = team;

        PlayerGameData.setGameInfo(gameInfo);

        String newName = "New Player";
        int chosenUnit = 3;

        String expected = "Successfully purchased, current balance 100.00";
        String actual = market.buyGoods(player, newName, chosenUnit);

        assertEquals(expected, actual);
        assertTrue(gameInfo.team.players.contains(player));
    }

    @Test
    void testBuyGoods_Player_InsufficientFunds() {
        MarketPlayer player = new MarketPlayer();
        player.price = 100.0;

        GameInfo gameInfo = new GameInfo();
        Team team = new Team();
        team.amount = 50.0;
        gameInfo.team = team;

        PlayerGameData.setGameInfo(gameInfo);

        String newName = "New Player";
        int chosenUnit = 3;

        String expected = "Sorry, your credit is running low!";
        String actual = market.buyGoods(player, newName, chosenUnit);

        assertEquals(expected, actual);
        assertFalse(gameInfo.team.players.contains(player));

    }

    @Test
    void testIsHaveHighPlayer() {
        assertFalse(market.isHaveHighPlayer());

        market.setHaveHighPlayer(true);

        assertTrue(market.isHaveHighPlayer());
    }
}

