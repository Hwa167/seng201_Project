package com.dd.playgame.gui;

import javax.swing.*;

public class GameController {

    private static Window rootWindow;

    public static void switchPanel(JPanel panel) {
        rootWindow.switchPanel(panel);
    }

    public static void main(String[] args) {
        Window window = new Window();
        rootWindow = window;

        switchPanel(new MainScreen());
    }

    public static void quit() {
        rootWindow.close();
    }
}
