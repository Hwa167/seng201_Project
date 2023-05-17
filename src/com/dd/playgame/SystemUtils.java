package com.dd.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SystemUtils {

    public static  AtomicInteger root_index = new AtomicInteger(1);

    public static AtomicInteger player_index = root_index;
    public static AtomicInteger consumable_index = root_index;

    public static Team getTeam() {

        List<BasketballPlayer> players = new ArrayList<>();
        createPlayer(players);

        List<BasketballPlayer> reserves = new ArrayList<>();
        createPlayer(reserves);

        Team team = new Team();
        team.name = "系统队伍";
        team.players = players;
        team.reserves = reserves;

        return team;
    }

    private static void createPlayer(List<BasketballPlayer> players) {
        Random random = new Random();

        for (PlayerRole role : PlayerRole.values()) {
            BasketballPlayer player = new BasketballPlayer();
            player.num = player_index.getAndIncrement();
            player.name = player.num + "号";
            player.role = role;
            player.initStrength = (int) (random.nextDouble() * 10000) / 100.0;
            player.initDefense = (int) (random.nextDouble() * 10000) / 100.0;
            player.currentStrength = player.initStrength;
            player.currentDefense = player.initDefense;
            player.endurance = BasketBallConfig.ENDURANCE_MAX;
            players.add(player);
        }
    }
}
