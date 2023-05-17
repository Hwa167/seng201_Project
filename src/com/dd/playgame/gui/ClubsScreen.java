package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.Team;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClubsScreen extends JPanel {

    private final JList reserveList;
    private final JList activeList;
    private Team team;

    /**
     * Create the panel.
     */
    public ClubsScreen() {
        this.team = PlayerGameData.getGameInfo().team;

        setLayout(null);

        JLabel lblAthletesOfClub = new JLabel("Athletes Of Club");
        lblAthletesOfClub.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblAthletesOfClub.setBounds(295, 10, 217, 55);
        add(lblAthletesOfClub);

        JLabel lblActivePlayers = new JLabel("Active Players");
        lblActivePlayers.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblActivePlayers.setBounds(85, 60, 150, 40);
        add(lblActivePlayers);

        JScrollPane jsp = new JScrollPane((Component) null);
        jsp.setBounds(75, 100, 705, 120);
        add(jsp);

        List<String> showPlayers = new ArrayList<>();
        for (Player player : team.players) {
            showPlayers.add(player.formatBasic());
        }
        activeList = new JList(showPlayers.toArray());
        activeList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component itemComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                itemComponent.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                return itemComponent;
            }
        });
        jsp.setViewportView(activeList);

        JLabel lblReservePlayers = new JLabel("Reserve Players");
        lblReservePlayers.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblReservePlayers.setBounds(85, 230, 150, 40);
        add(lblReservePlayers);

        JScrollPane jsp2 = new JScrollPane((Component) null);
        jsp2.setBounds(75, 275, 705, 120);
        add(jsp2);

        List<String> showReservePlayers = new ArrayList<>();
        for (Player player : team.reserves) {
            showReservePlayers.add(player.formatBasic());
        }
        reserveList = new JList(showReservePlayers.toArray());
        reserveList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component itemComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                itemComponent.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                return itemComponent;
            }
        });
        jsp2.setViewportView(reserveList);

        JButton btnExchange = new JButton("Exchange");
        btnExchange.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnExchange.setBounds(110, 410, 118, 42);
        btnExchange.addActionListener(e -> {
            exchange(btnExchange);
        });
        add(btnExchange);

        JButton btnAddPlayer = new JButton("AddToActive");
        btnAddPlayer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnAddPlayer.setBounds(263, 410, 135, 42);
        btnAddPlayer.addActionListener(e -> {
            addToActive(btnAddPlayer);
        });
        add(btnAddPlayer);

        JButton btnRemovePlayer = new JButton("MoveToReserve");
        btnRemovePlayer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnRemovePlayer.setBounds(433, 410, 160, 42);
        btnRemovePlayer.addActionListener(e -> {
            moveToReserve(btnRemovePlayer);
        });
        add(btnRemovePlayer);

        JButton btnBack = new JButton("Back\r\n");
        btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnBack.setBounds(628, 410, 105, 42);
        btnBack.addActionListener(e -> GameController.switchPanel(new PlaysScreen()));
        add(btnBack);

        JLabel lblBalance = new JLabel("Balance   -   " + team.getAmountStr());
        lblBalance.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblBalance.setBounds(580, 52, 200, 40);
        add(lblBalance);
    }

    /**
     * Choose a active player to reserve player
     * @param button
     */
    private void moveToReserve(JButton button) {
        int activeIndex = activeList.getSelectedIndex();
        if (activeIndex != -1) {
            Player activePlayer = team.players.get(activeIndex);
            team.players.remove(activePlayer);
            team.reserves.add(activePlayer);
            refreshData();
        } else {
            new MessageFrame("Error", "Please select the athlete to rest!", false, button);
        }
    }

    /**
     * Choose a reserve player to active player
     * @param button
     */
    private void addToActive(JButton button) {
        int reserveIndex = reserveList.getSelectedIndex();
        if (reserveIndex != -1) {
            Player reservePlayer = team.reserves.get(reserveIndex);
            if (reservePlayer.endurance > 0) {
                Optional<Player> first = team.players.stream().filter(item -> item.role == reservePlayer.role).findFirst();
                first.ifPresent(it -> {
                    team.players.remove(it);
                    team.reserves.add(it);
                });
                team.reserves.remove(reservePlayer);
                team.players.add(reservePlayer);
                refreshData();
            } else {
                new MessageFrame("Error", "The substitute player no longer has the endurance to play, please choose again!", true, button);
            }
        } else {
            new MessageFrame("Error", "Please select the reserve athlete to active!", false, button);
        }
    }

    /**
     * exchange active player and reserve player
     *
     * @param button
     */
    private void exchange(JButton button) {
        int activeIndex = activeList.getSelectedIndex();
        int reserveIndex = reserveList.getSelectedIndex();
        if (activeIndex != -1 && reserveIndex != -1) {
            Player reservePlayer = team.reserves.get(reserveIndex);
            if (reservePlayer.endurance > 0) {
                Player activePlayer = team.players.get(activeIndex);
                if (activePlayer.role == reservePlayer.role) {
                    team.players.remove(activePlayer);
                    team.players.add(reservePlayer);
                    team.reserves.remove(reservePlayer);
                    team.reserves.add(activePlayer);
                    refreshData();
                } else {
                    new MessageFrame("Error", "The exchange cannot be completed due to different roles!", true, button);
                }
            } else {
                new MessageFrame("Error", "The substitute player no longer has the endurance to play, please choose again!", true, button);
            }
        } else {
            new MessageFrame("Error", "Please select the team member to exchange!", false, button);
        }
    }

    /**
     * Refresh the data displayed on the panel
     */
    public void refreshData() {
        List<String> showPlayers = new ArrayList<>();
        for (Player player : team.players) {
            showPlayers.add(player.formatBasic());
        }
        activeList.setListData(showPlayers.toArray());

        List<String> showReservePlayers = new ArrayList<>();
        for (Player player : team.reserves) {
            showReservePlayers.add(player.formatBasic());
        }
        reserveList.setListData(showReservePlayers.toArray());
    }
}
