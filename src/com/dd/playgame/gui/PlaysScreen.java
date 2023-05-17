package com.dd.playgame.gui;

import com.dd.playgame.application.DataHandler;
import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.application.PlayerRole;
import com.dd.playgame.bean.Team;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.setBounds(71, 207, 117, 42);
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
            if (PlayerGameData.getGameInfo().isLastWeek()) {
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
        add(btnShop_1);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PlaysScreen.class.getResource("/Image/KK.jpg")));
        lblNewLabel.setBounds(38, -46, 848, 572);
        add(lblNewLabel);
    }

    private void toBattle(JButton button) {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        //检查队伍是否符合规则
        ArrayList<String> absentRoles = new ArrayList<>();
        for (PlayerRole role : PlayerRole.values()) {
            boolean exists = gameInfo.team.players.stream().anyMatch(item -> item.role == role);
            if (!exists) {
                absentRoles.add(role.value);
            }
        }
        if (!absentRoles.isEmpty()) {
            new MessageFrame("Tips", "There is a shortage of athletes with roles "+
                    absentRoles
                    +" in the team, go to the market and take a look!", true, null);
            return;
        }

        boolean checkEndurance = gameInfo.team.players.stream().filter(item -> item.endurance > 0).count() == 5;
        if (!checkEndurance) {
            new MessageFrame("Tips", "Some members of the team have insufficient endurance and cannot participate in the competition!", true, null);
            return;
        }
        GameController.switchPanel(new BattleScreen());
    }

    private void refreshData() {
        lblWeek.setText(getTitle());
    }

    private String getTitle() {
        GameInfo gameInfo = PlayerGameData.getGameInfo();
        return "Week  -  " + gameInfo.cycle + " / " + gameInfo.allCycle + "   Team: " + gameInfo.team.name + "   Scores  -  " + gameInfo.team.integral;
    }
}
