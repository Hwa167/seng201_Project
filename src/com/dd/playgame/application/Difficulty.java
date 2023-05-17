package com.dd.playgame.application;

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
