package com.dd.playgame.application;

import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.Team;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TeamGenerator {

    private static final String[] teamNames = {"Lions", "Tigers", "Bears", "Eagles", "Wolves", "Panthers", "Sharks", "Dragons", "Phoenix", "Thunder"};

    private static final String[] names = {"John", "David", "Michael", "Daniel", "Steven", "Robert", "William", "Christopher", "James", "Joseph", "Smith", "Johnson", "Brown", "Davis", "Wilson", "Taylor", "Anderson", "Thompson", "Harris", "Martin"};

    private static final List<String> useNames = new ArrayList<>();

    private static final double[][] ATTR_RANGE = {
            {60d, 80d},   // easy
            {70d, 90d},   // normal
            {80d, 100d}   // hard
    };

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

    private static double getRandomDouble(double min, double max) {
        double randomDouble = ThreadLocalRandom.current().nextDouble(min, max + 0.01);
        return BigDecimal.valueOf(randomDouble)
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    private static Player generatePlayer(int difficulty) {
        double strength = getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);
        double defense = getRandomDouble(ATTR_RANGE[difficulty][0], ATTR_RANGE[difficulty][1]);

        String name = names[getRandomInt(0, names.length - 1)];
        while (useNames.contains(name)) {
            name = names[getRandomInt(0, names.length - 1)];
        }
        useNames.add(name);

        Player player = new Player();
        player.name = name;
        player.strength = strength;
        player.defense = defense;
        return player;
    }

    public static Team generateTeam(GameInfo gameInfo) {
        int difficulty = gameInfo.difficulty.getNum();

        useNames.clear();

        Team team = new Team();
        team.name = teamNames[getRandomInt(0, teamNames.length - 1)];

        List<Player> players = new ArrayList<>();
        for (PlayerRole value : PlayerRole.values()) {
            Player player = generatePlayer(difficulty);
            player.num = gameInfo.player_index.getAndIncrement();
            player.role = value;
            players.add(player);
        }
        team.players = players;

        return team;
    }
}
