package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.Team;
import com.dd.playgame.generator.TeamGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StadiumScreen extends JPanel {

    private final JList teamList;
    private final GameInfo gameInfo;

    /**
     * Create the panel.
     */
    public StadiumScreen() {
        setLayout(null);

        gameInfo = PlayerGameData.getGameInfo();

        JLabel lblName = new JLabel("You will use <dynamic>");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblName.setBounds(58, 319, 413, 56);
        lblName.setVisible(false);
        add(lblName);

        JLabel lblUseItems = new JLabel("System Teams");
        lblUseItems.setHorizontalAlignment(SwingConstants.CENTER);
        lblUseItems.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblUseItems.setBounds(58, 10, 740, 56);
        add(lblUseItems);

        JScrollPane jsp = new JScrollPane((Component) null);
        jsp.setBounds(58, 93, 740, 216);
        add(jsp);

        // If there is currently no system team, generate it
        if (gameInfo.systemTeams.isEmpty()) {
            gameInfo.systemTeams.addAll(TeamGenerator.generateTeams(gameInfo));
        }

        List<String> showTeams = new ArrayList<>();
        for (Team systemTeam : gameInfo.systemTeams) {
            showTeams.add(systemTeam.formatBasic());
        }

        teamList = new JList(showTeams.toArray());
        teamList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component itemComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                itemComponent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                return itemComponent;
            }
        });
        jsp.setViewportView(teamList);

        JButton btnEnter = new JButton("Battle");
        btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnEnter.setBackground(Color.WHITE);
        btnEnter.setBounds(681, 319, 117, 42);
        btnEnter.addActionListener(e -> battle(btnEnter));
        add(btnEnter);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnBack.setBackground(Color.WHITE);
        btnBack.setBounds(681, 431, 117, 42);
        btnBack.addActionListener(e -> GameController.switchPanel(new PlaysScreen()));
        add(btnBack);

    }

    /**
     * Choose a system team as the opponent to battle
     * @param button
     */
    private void battle(JButton button) {
        int teamIndex = teamList.getSelectedIndex();
        if (teamIndex == -1) {
            new MessageFrame("Error", "Please select an opponent!", false, button);
            return;
        }

        if (PlayerGameData.canBattle(button)) {
            GameController.switchPanel(new BattleScreen(gameInfo.systemTeams.get(teamIndex)));
        }
    }
}
