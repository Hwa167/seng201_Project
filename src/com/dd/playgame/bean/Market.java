package com.dd.playgame.bean;

import com.dd.playgame.generator.GoodsGenerator;
import com.dd.playgame.application.PlayerGameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a Java Bean class for representing a market.
 * It includes properties for the market's players, consumables, allGoods, infoList and haveHighPlayer.
 */
public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * List of players for sale
     */
    public List<MarketPlayer> players;

    /**
     * List of consumables for sale
     */
    public List<MarketConsumable> consumables;

    /**
     * All goods
     */
    public List<Object> allGoods;

    /**
     * Goods information for display
     */
    public List<String> infoList;

    /**
     * have a high player
     */
    public boolean haveHighPlayer;

    /**
     * Refresh the list of products sold in the market
     *
     * @param gameInfo
     */
    public void refresh(GameInfo gameInfo) {
        this.players = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.allGoods = new ArrayList<>();
        this.infoList = new ArrayList<>();
        this.haveHighPlayer = false;

        GoodsGenerator.generatePlayers(gameInfo, this);
        GoodsGenerator.generateConsumables(gameInfo, this);

        refreshGoods();
    }

    /**
     * Refresh product information
     */
    public void refreshGoods() {
        allGoods.clear();
        infoList.clear();
        for (MarketPlayer player : players) {
            allGoods.add(player);
            infoList.add(player.format());
        }
        for (MarketConsumable consumable : consumables) {
            allGoods.add(consumable);
            infoList.add(consumable.format());
        }
    }

    /**
     * Get all goods
     *
     * @return goods
     */
    public List<Object> getAllGoods() {
        return allGoods;
    }

    /**
     * get goods information for display
     *
     * @return goods information
     */
    public List<String> getGoodsInfo() {
        return infoList;
    }

    /**
     * Removing products from the market
     *
     * @param buyItem Item that need to be removed
     */
    public void removeGoods(Object buyItem) {
        if (buyItem instanceof MarketPlayer) {
            players.remove(buyItem);
        } else if (buyItem instanceof MarketConsumable) {
            consumables.remove(buyItem);
        }
        refreshGoods();
    }

    /**
     * Purchase consumables
     *
     * @param item consumable
     * @return info
     */
    public String buyGoods(MarketConsumable item) {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        double currentAmount = gameInfo.team.amount;
        if (currentAmount >= item.price) {
            gameInfo.team.buyConsumable(item);
            removeGoods(item);
            return "Successfully purchased, current balance " + gameInfo.team.getAmountStr();
        } else {
            return "Sorry, your credit is running low!";
        }
    }

    /**
     * Purchase player
     *
     * @param item      player
     * @param newName   New names for athletes
     * @param choseUnit Units placed after successful purchase
     * @return info
     */
    public String buyGoods(MarketPlayer item, String newName, int choseUnit) {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        double currentAmount = gameInfo.team.amount;
        if (currentAmount >= item.price) {
            if (newName != null && !newName.trim().isEmpty()) {
                item.name = newName;
            }
            gameInfo.team.buyPlayer(item, choseUnit);
            removeGoods(item);
            return "Successfully purchased, current balance " + gameInfo.team.getAmountStr();
        } else {
            return "Sorry, your credit is running low!";
        }
    }

    /**
     * Determine if you have a high athlete
     *
     * @return
     */
    public boolean isHaveHighPlayer() {
        return haveHighPlayer;
    }

    /**
     * high athletes appear
     * @param haveHighPlayer appear
     */
    public void setHaveHighPlayer(boolean haveHighPlayer) {
        this.haveHighPlayer = haveHighPlayer;
    }
}
