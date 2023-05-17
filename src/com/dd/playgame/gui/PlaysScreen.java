package com.dd.playgame.gui;

import com.dd.playgame.application.DataHandler;
import com.dd.playgame.application.GameController;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.application.PlayerGameData;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Optional;

public class PlaysScreen extends JPanel {

    private final JLabel lblWeek;
    private JButton btnLoad;

    /**
     * Create the panel.
     */
    public PlaysScreen() {
        setLayout(null);

        JButton btnNewButton = new JButton("Stadium");
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNewButton.setBackground(new Color(240, 240, 240));
        btnNewButton.setBounds(71, 207, 117, 42);
        btnNewButton.addActionListener(e -> GameController.switchPanel(new StadiumScreen()));
        add(btnNewButton);

        JButton btnClub = new JButton("Club\r\n");
        btnClub.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnClub.setBounds(518, 392, 89, 42);
        btnClub.addActionListener(e -> GameController.switchPanel(new ClubsScreen()));
        add(btnClub);

        JButton btnShop = new JButton("Shop");
        btnShop.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnShop.setBounds(71, 328, 89, 42);
        btnShop.addActionListener(e -> GameController.switchPanel(new ShopsScreen()));
        add(btnShop);

        JButton btnMatch = new JButton("Match");
        btnMatch.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnMatch.setBounds(267, 328, 89, 42);
        btnMatch.addActionListener(e -> {
            toBattle(btnMatch);
        });
        add(btnMatch);

        JButton btnRest = new JButton("Rest");
        btnRest.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnRest.setBounds(71, 105, 89, 42);
        btnRest.addActionListener(e -> {
            if (PlayerGameData.isLastWeek()) {
                GameController.switchPanel(new GameOverScreen());
            } else {
                PlayerGameData.nextWeek();
                GameController.switchPanel(new RandomsScreen());
            }
        });
        add(btnRest);

        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnSave.setBounds(664, 181, 89, 42);
        btnSave.addActionListener(e -> {
            DataHandler.saveData();
            Optional.ofNullable(btnLoad).ifPresent(it -> it.setEnabled(DataHandler.checkSaved()));
        });
        add(btnSave);

        btnLoad = new JButton("Load");
        btnLoad.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnLoad.setBounds(664, 255, 89, 42);
        btnLoad.addActionListener(e -> {
            DataHandler.loadData();
            refreshData();
        });
        btnLoad.setEnabled(DataHandler.checkSaved());

        add(btnLoad);

        JButton btnQuit = new JButton("Quit");
        btnQuit.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnQuit.setBounds(664, 328, 89, 42);
        btnQuit.addActionListener(e -> GameController.quit());
        add(btnQuit);

        lblWeek = new JLabel(getTitle());
        lblWeek.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblWeek.setBounds(150, 22, 600, 55);
        add(lblWeek);

        JButton btnShop_1 = new JButton("Store");
        btnShop_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnShop_1.setBounds(384, 165, 89, 42);
        btnShop_1.addActionListener(e -> GameController.switchPanel(new StoresScreen()));
        add(btnShop_1);

        JLabel lblNewLabel = new JLabel("");
//        lblNewLabel.setIcon(new ImageIcon(PlaysScreen.class.getResource("/Image/KK.jpg")));
        lblNewLabel.setBounds(38, -46, 848, 572);
        add(lblNewLabel);
        
        JButton btnShop_1_1 = new JButton("Store");
        btnShop_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnShop_1_1.setBounds(384, 165, 89, 42);
        add(btnShop_1_1);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(PlaysScreen.class.getResource("/Image/KK.jpg")));
        lblNewLabel_1.setBounds(10, 22, 800, 467);
        add(lblNewLabel_1);
    }

    /**
     * Conduct a random match
     *
     * @param button
     */
    private void toBattle(JButton button) {
        if (PlayerGameData.canBattle(button)) {
            GameController.switchPanel(new BattleScreen());
        }
    }

    /**
     * Refresh the data displayed on the panel
     */
    private void refreshData() {
        lblWeek.setText(getTitle());
    }

    /**
     * Panel Display Title
     *
     * @return title content
     */
    private String getTitle() {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        return "Week  -  " + gameInfo.cycle + " / " + gameInfo.allCycle + "   Team: " + gameInfo.team.name + "   Scores  -  " + gameInfo.team.integral;
    }
}
