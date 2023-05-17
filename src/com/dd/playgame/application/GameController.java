package com.dd.playgame.application;

import com.dd.playgame.gui.MainScreen;
import com.dd.playgame.gui.Window;

import javax.swing.*;

/**
 * Game Main Form Controller
 */
public class GameController {

    private static Window rootWindow;

    /**
     * switch panel
     * @param panel target panel
     */
    public static void switchPanel(JPanel panel) {
        rootWindow.switchPanel(panel);
    }
    /**
     * The main method of the game.
     *
     * @param args The command-line arguments (not used)
     */
    public static void main(String[] args) {
        Window window = new Window();
        rootWindow = window;

        switchPanel(new MainScreen());
    }

    /**
     * Exit the game
     */
    public static void quit() {
        rootWindow.close();
    }
}
