package com.dd.playgame.test;

import com.dd.playgame.bean.BattleResult;
import com.dd.playgame.bean.Difficulty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleResultTest {

    @Test
    void testTeam1WinHardDifficulty() {
        BattleResult result = new BattleResult(80, 70, Difficulty.HARD);

        assertTrue(result.isTeam1Win());
        assertEquals(600, result.getTeam1Points());
        assertEquals(300, result.getTeam2Points());
        assertEquals(100, result.getTeam1Money());
        assertEquals(50, result.getTeam2Money());
    }

    @Test
    void testTeam2WinNormalDifficulty() {
        BattleResult result = new BattleResult(60, 70, Difficulty.NORMAL);

        assertFalse(result.isTeam1Win());
        assertEquals(200, result.getTeam1Points());
        assertEquals(400, result.getTeam2Points());
        assertEquals(75, result.getTeam1Money());
        assertEquals(150, result.getTeam2Money());
    }

    @Test
    void testDrawEasyDifficulty() {
        BattleResult result = new BattleResult(50, 50, Difficulty.EASY);

        assertFalse(result.isTeam1Win());
        assertEquals(100, result.getTeam1Points());
        assertEquals(100, result.getTeam2Points());
        assertEquals(100, result.getTeam1Money());
        assertEquals(100, result.getTeam2Money());
    }

    @Test
    void testCloseScoreDifference() {
        BattleResult result = new BattleResult(80, 75, Difficulty.NORMAL);

        assertTrue(result.isTeam1Win());
        assertEquals(400, result.getTeam1Points());
        assertEquals(200, result.getTeam2Points());
        assertEquals(150, result.getTeam1Money());
        assertEquals(120, result.getTeam2Money());
    }

    @Test
    void testLargeScoreDifference() {
        BattleResult result = new BattleResult(90, 60, Difficulty.HARD);

        assertTrue(result.isTeam1Win());
        assertEquals(600, result.getTeam1Points());
        assertEquals(300, result.getTeam2Points());
        assertEquals(100, result.getTeam1Money());
        assertEquals(40, result.getTeam2Money());
    }

    @Test
    void testNegativeScores() {
        BattleResult result = new BattleResult(-80, -70, Difficulty.EASY);

        assertTrue(result.isTeam1Win());
        assertEquals(100, result.getTeam1Points());
        assertEquals(50, result.getTeam2Points());
        assertEquals(200, result.getTeam1Money());
        assertEquals(100, result.getTeam2Money());
    }
}
