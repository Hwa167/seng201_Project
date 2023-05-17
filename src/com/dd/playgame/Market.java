package com.dd.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Market {

    private final Random random = new Random();

    public List<MarketPlayer> players;

    public List<MarketConsumable> consumables;

    public Market() {
        refresh();
    }

    public void refresh() {
        this.players = new ArrayList<>();
        this.consumables = new ArrayList<>();

        randomPlayers();
        randomConsumables();
    }

    private void randomConsumables() {
        int consumableCounts = random.nextInt(4) + 3;
        ConsumableType[] consumableTypes = ConsumableType.values();
        for (int i = 0; i < consumableCounts; i++) {
            ConsumableType consumableType = consumableTypes[random.nextInt(consumableTypes.length)];
            MarketConsumable consumable = new MarketConsumable();
            consumable.num = SystemUtils.consumable_index.getAndIncrement();
            consumable.name = consumableType.name;
            consumable.description = consumableType.description;
            consumable.type = consumableType;
            consumable.effect = (int) (random.nextDouble() * 1000) / 100.0;

            consumable.price = 10.25;
            consumables.add(consumable);
        }
    }

    private void randomPlayers() {
        int playerCounts = random.nextInt(3) + 3;
        PlayerRole[] values = PlayerRole.values();
        for (int i = 0; i < playerCounts; i++) {
            PlayerRole role = values[random.nextInt(values.length)];
            MarketPlayer player = new MarketPlayer();
            player.num = SystemUtils.player_index.getAndIncrement();
            player.name = player.num + "号";
            player.role = role;
            player.initStrength = (int) (random.nextDouble() * 10000) / 100.0;
            player.initDefense = (int) (random.nextDouble() * 10000) / 100.0;
            player.price = 88.80;
            players.add(player);
        }
    }

    public void show() {
        System.out.println("市场资源列表:");
        System.out.println("运动员: ");
        PrintUtils.showPlayers(players);
        System.out.println("消耗品: ");
        PrintUtils.showConsumables(consumables);
    }
}
