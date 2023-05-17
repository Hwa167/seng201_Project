package com.dd.playgame.application;

import com.dd.playgame.bean.Market;
import com.dd.playgame.bean.Team;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public final AtomicInteger root_index = new AtomicInteger(1);

    public final AtomicInteger player_index = root_index;

    public final AtomicInteger consumable_index = root_index;

    // game difficulty
    public Difficulty difficulty;

    public int allCycle;

    public int cycle;

    public Team team;

    public Market market;

    public boolean generatorHighPlayer;

    public void nextWeek() {
        generatorHighPlayer = false;

        cycle++;
        market.refresh(this);
        team.refreshPlayerState();
    }

    public boolean isLastWeek() {
        return cycle == allCycle;
    }

    public boolean capacityUp() {
        return ThreadLocalRandom.current().nextInt(1, 101) == 1;
    }

    public boolean gaveUp(){
        return ThreadLocalRandom.current().nextInt(1, 1001) == 1;
    }

    public boolean generatorHighPlayer() {
        return ThreadLocalRandom.current().nextDouble() > 0.9
                && ThreadLocalRandom.current().nextDouble() > 0.9;
    }

    public boolean generatorHigh2Player() {
        return ThreadLocalRandom.current().nextDouble() > 0.9;
    }
}
