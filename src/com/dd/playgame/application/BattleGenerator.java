package com.dd.playgame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BattleGenerator {

    private static final List<String> winGreetings = new ArrayList<>();

    private static final List<String> loseGreetings = new ArrayList<>();

    static {
        winGreetings.add("Great shot! You've scored a two-pointer!");
        winGreetings.add("Nice block! You've prevented the other team from scoring.");
        winGreetings.add("Excellent teamwork! You and your teammate made a great play.");
        winGreetings.add("Great job! You defended well and stole the ball from the opponent.");
        winGreetings.add("Nice shot! You scored a three-pointer and brought the team closer to victory.");
        winGreetings.add("Watch out! The opponent is getting close to the basket, make sure to block them.");
        winGreetings.add("Well done! You grabbed a rebound and gave the team another chance to score.");
        winGreetings.add("Stay focused! The opponent is trying to distract you, but you can handle it.");

        loseGreetings.add("Oops, the ball slipped out of your hands. Better luck next time!");
        loseGreetings.add("Keep up the good work! You're doing great.");
        loseGreetings.add("Uh-oh, the other team stole the ball. Don't give up, you can still turn this around!");
        loseGreetings.add("Be careful! You committed a foul and gave the opponent a free throw opportunity.");
        loseGreetings.add("Keep up the good work! You are playing aggressively and making some great passes.");
        loseGreetings.add("You missed the shot, but don't worry. Keep trying and you'll get it next time.");
    }

    public static String getGreeting(boolean win) {
        if (win) {
            return winGreetings.get(ThreadLocalRandom.current().nextInt(winGreetings.size()));
        } else {
            return loseGreetings.get(ThreadLocalRandom.current().nextInt(loseGreetings.size()));
        }
    }
}
