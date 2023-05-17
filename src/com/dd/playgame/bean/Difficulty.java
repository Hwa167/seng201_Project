package com.dd.playgame.bean;

/**
 * Game difficulty
 */
public enum Difficulty {

    EASY(0),
    NORMAL(1),
    HARD(2);

    private final int num;

    Difficulty(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
