package com.dd.playgame.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a Java Bean class for representing a game info.
 * It includes properties for the info's difficulty, userTeam, allCycle, currentCycle and market.
 */
public final class GameInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public final AtomicInteger root_index = new AtomicInteger(1);

    public AtomicInteger player_index = root_index;

    public final AtomicInteger consumable_index = root_index;

    // game difficulty
    public Difficulty difficulty;

    /**
     * Total game cycle
     */
    public int allCycle;

    /**
     * current  game cycle
     */
    public int cycle;

    /**
     * user team
     */
    public Team team;

    /**
     * system market
     */
    public Market market;

    public List<Team> systemTeams = new ArrayList<>();

    /**
     * Repair the team and move on to the next week
     */
    public void nextWeek() {
        systemTeams.clear();
        cycle++;
        market.refresh(this);
        team.refreshPlayerState();
    }

    /**
     * Check if this week is the last week
     * @return
     */
    public boolean isLastWeek() {
        return cycle == allCycle;
    }
}
