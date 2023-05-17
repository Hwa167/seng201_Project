package com.dd.playgame;

import java.util.List;

public class PrintUtils {

    public static void showPlayers(List<? extends Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("num: ").append(player.num).append("\t");
            stringBuilder.append("name: ").append(player.name).append("\t");
            stringBuilder.append("role: ").append(player.role.name()).append("\t");
            if (player instanceof BasketballPlayer) {
                BasketballPlayer temp = ((BasketballPlayer) player);
                stringBuilder.append("current strength: ").append(temp.currentStrength).append("\t");
                stringBuilder.append("current defense: ").append(temp.currentDefense).append("\t");
                stringBuilder.append("endurance: ").append(temp.endurance).append("\t");
            } else if (player instanceof MarketPlayer) {
                stringBuilder.append("init strength: ").append(player.initStrength).append("\t");
                stringBuilder.append("init defense: ").append(player.initDefense).append("\t");
                stringBuilder.append("price: ").append(((MarketPlayer) player).price).append("\t");
            }
            System.out.println(stringBuilder);
        }
    }

    public static void showConsumables(List<? extends Consumable> consumables) {
        for (int i = 0; i < consumables.size(); i++) {
            Consumable consumable = consumables.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("num: ").append(consumable.num).append("\t");
            stringBuilder.append("name: ").append(consumable.name).append("\t");
            stringBuilder.append("desc: ").append(consumable.description).append("\t");
            stringBuilder.append("effect: ").append(consumable.effect).append("\t");
            if (consumable instanceof MarketConsumable) {
                stringBuilder.append("price: ").append(((MarketConsumable) consumable).price).append("\t");
            }
            System.out.println(stringBuilder);
        }
    }
}
