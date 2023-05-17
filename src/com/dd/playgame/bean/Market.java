package com.dd.playgame.bean;

import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.MarketGenerator;
import com.dd.playgame.application.PlayerGameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<MarketPlayer> players;

    public List<MarketConsumable> consumables;

    private List<Object> goods;

    private List<String> infoList;

    public void refresh(GameInfo gameInfo) {
        this.players = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.goods = new ArrayList<>();
        this.infoList = new ArrayList<>();

        players.addAll(MarketGenerator.generatePlayers(gameInfo));
        consumables.addAll(MarketGenerator.generateConsumables(gameInfo));

        refreshGoods();
    }

    private void refreshGoods() {
        goods.clear();
        infoList.clear();
        for (MarketPlayer player : players) {
            goods.add(player);
            infoList.add(player.format());
        }
        for (MarketConsumable consumable : consumables) {
            goods.add(consumable);
            infoList.add(consumable.format());
        }
    }

    public List<Object> getGoods() {
        return goods;
    }

    public List<String> getGoodsInfo() {
        return infoList;
    }

    public void removeGoods(Object buyItem) {
        if (buyItem instanceof MarketPlayer) {
            players.remove(buyItem);
        } else if (buyItem instanceof MarketConsumable) {
            consumables.remove(buyItem);
        }
        refreshGoods();
    }

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

    public String buyGoods(MarketPlayer item, String newName, int choseUnit) {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        double currentAmount = gameInfo.team.amount;
        if (currentAmount >= item.price) {
            if (newName != null && !newName.trim().isEmpty()) {
                item.name = newName;
            }
            gameInfo.team.joinPlayer(item, choseUnit);
            removeGoods(item);
            return "Successfully purchased, current balance " + gameInfo.team.getAmountStr();
        } else {
            return "Sorry, your credit is running low!";
        }
    }
}
