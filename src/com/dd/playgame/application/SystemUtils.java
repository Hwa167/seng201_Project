package com.dd.playgame.application;

import java.util.concurrent.atomic.AtomicInteger;

public class SystemUtils {

    public static  AtomicInteger root_index = new AtomicInteger(1);

    public static AtomicInteger player_index = root_index;
    public static AtomicInteger consumable_index = root_index;

}
