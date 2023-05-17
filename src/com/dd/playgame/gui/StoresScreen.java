package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.Consumable;
import com.dd.playgame.gui.frame.ChosePlayerFrame;

import javax.swing.*;
import java.awt.Font;
import java.awt.Component;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class StoresScreen extends JPanel {

    private final JList consumableList;

    /**
     * Create the panel.
     */
    public StoresScreen() {
        setLayout(null);

        JLabel lblStore = new JLabel("Store\r\n");
        lblStore.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblStore.setBounds(351, 10, 99, 62);
        add(lblStore);

        JLabel lblYouCanUse = new JLabel("You Can Use These Items:");
        lblYouCanUse.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblYouCanUse.setBounds(38, 89, 319, 62);
        add(lblYouCanUse);

        JScrollPane jsp = new JScrollPane((Component) null);
        jsp.setBounds(38, 161, 730, 235);
        add(jsp);

        List<String> showConsumables = new ArrayList<>();
        for (Consumable consumable : PlayerGameData.getGameInfo().team.consumables) {
            showConsumables.add(consumable.formatBasic());
        }
        consumableList = new JList(showConsumables.toArray());
        consumableList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component itemComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                itemComponent.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                return itemComponent;
            }
        });
        jsp.setViewportView(consumableList);

        JButton btnEnter = new JButton("Enter");
        btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnEnter.setBackground(Color.WHITE);
        btnEnter.setBounds(204, 406, 117, 42);
        btnEnter.addActionListener(e -> applyConsumable(btnEnter));
        add(btnEnter);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnBack.setBackground(Color.WHITE);
        btnBack.setBounds(506, 406, 117, 42);
        btnBack.addActionListener(e -> GameController.switchPanel(new PlaysScreen()));
        add(btnBack);
    }

    /**
     * Select consumables to be used by athletes
     *
     * @param button
     */
    private void applyConsumable(JButton button) {
        int consumableIndex = consumableList.getSelectedIndex();
        if (consumableIndex != -1) {
            Consumable consumable = PlayerGameData.getGameInfo().team.consumables.get(consumableIndex);
            new ChosePlayerFrame(this, consumable);
        } else {
            new MessageFrame("Error", "Please select the consumable!", false, button);
        }
    }

    /**
     * Refresh the data displayed on the panel
     */
    public void refreshData() {
        List<String> showConsumables = new ArrayList<>();
        for (Consumable consumable : PlayerGameData.getGameInfo().team.consumables) {
            showConsumables.add(consumable.formatBasic());
        }
        consumableList.setListData(showConsumables.toArray());
    }
}
