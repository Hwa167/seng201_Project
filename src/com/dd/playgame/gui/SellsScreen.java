package com.dd.playgame.gui;

import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.Consumable;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.bean.Player;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.util.Optional;

public class SellsScreen extends JPanel {

    private final JLabel lblBalance;
    private final JTextPane textPane;

    private JTextField itemField;

    private final GameInfo gameInfo;

    /**
     * Create the panel.
     */
    public SellsScreen() {
        this.gameInfo = PlayerGameData.getGameInfo();

        setLayout(null);

        JLabel lblSell = new JLabel("Sell Items");
        lblSell.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblSell.setBounds(327, 0, 163, 55);
        add(lblSell);

        lblBalance = new JLabel("");
        lblBalance.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblBalance.setBounds(299, 65, 227, 40);
        add(lblBalance);

        textPane = new JTextPane();
        textPane.setFont(new Font("Dialog", Font.PLAIN, 17));
        textPane.setEditable(false);
        textPane.setBounds(42, 120, 734, 218);
        add(textPane);

        JLabel lblNewLabel1 = new JLabel("Please enter the number of the item you want to sell");
        lblNewLabel1.setBounds(80, 337, 520, 40);
        lblNewLabel1.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(lblNewLabel1);

        itemField = new JTextField();
        itemField.setBounds(544, 340, 179, 34);
        itemField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(itemField);

        JButton btnEnter = new JButton("Enter");
        btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnEnter.setBackground(Color.WHITE);
        btnEnter.setBounds(151, 394, 117, 42);
        btnEnter.addActionListener(e -> sell(btnEnter));
        add(btnEnter);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnBack.setBackground(Color.WHITE);
        btnBack.setBounds(518, 394, 117, 42);
        btnBack.addActionListener(e -> GameController.switchPanel(new ShopsScreen()));
        add(btnBack);

        loadData();
    }

    private void sell(JButton button) {
        int num;
        try {
            num = Integer.parseInt(itemField.getText());
        } catch (NumberFormatException e) {
            new MessageFrame("Error", "Enter a valid integer number!", false, button);
            return;
        }
        Optional<Player> optionalPlayer = gameInfo.team.reserves.stream().filter(item -> item.num == num).findFirst();
        Optional<Consumable> optionalConsumable = gameInfo.team.consumables.stream().filter(item -> item.num == num).findFirst();
        if (optionalPlayer.isPresent() || optionalConsumable.isPresent()) {
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                gameInfo.team.sellPlayer(player);
            }else {
                Consumable consumable = optionalConsumable.get();
                gameInfo.team.sellConsumable(consumable);
            }
            refreshData();
        }else {
            new MessageFrame("Error", "unknown number!", false, button);
        }
    }

    private void loadData() {
        refreshData();
    }

    private void refreshData() {
        lblBalance.setText("Balance   -   " + gameInfo.team.getAmountStr());

        StringBuilder stringBuilder = new StringBuilder();
        for (Player reserve : gameInfo.team.reserves) {
            String item = String.format("%-8s %-15s role:%-23s price:%-11s sellPrice:%-11s",
                    reserve.num, reserve.name, reserve.role.value,
                    ((MarketPlayer) reserve).getPriceStr(), ((MarketPlayer) reserve).getSellPriceStr());
            stringBuilder.append(item).append("\n");
        }
        for (Consumable consumable : gameInfo.team.consumables) {
            String item = String.format("%-8s %-24s effect:%-11s  price:%-11s sellPrice:%-11s",
                    consumable.num, consumable.name, consumable.effect,
                    ((MarketConsumable) consumable).getPriceStr(),((MarketConsumable) consumable).getSellPriceStr());
            stringBuilder.append(item).append("\n");
        }
        textPane.setText(stringBuilder.toString());
    }
}
