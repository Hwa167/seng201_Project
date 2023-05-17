package com.dd.playgame.gui;

import com.dd.playgame.application.ConsumableType;
import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.MarketGenerator;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.Player;

import javax.swing.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomsScreen extends JPanel {

    /**
     * Create the panel.
     */
    public RandomsScreen() {
        setLayout(null);

        JLabel lblHaveAGood = new JLabel("Have A Good Week");
        lblHaveAGood.setFont(new Font("Times New Roman", Font.BOLD, 50));
        lblHaveAGood.setBounds(198, 72, 490, 100);
        add(lblHaveAGood);

        StringBuilder stringBuilder = new StringBuilder();
        GameInfo gameInfo = PlayerGameData.getGameInfo();

        if (gameInfo.generatorHighPlayer) {
            stringBuilder.append("Congratulations, the market has produced more advanced athletes!\n");
        }

        List<Integer> playerNums = new ArrayList<>();
        for (Player player : gameInfo.team.players) {
            if (gameInfo.capacityUp()) {
                playerNums.add(player.num);
                boolean next = ThreadLocalRandom.current().nextBoolean();
                MarketConsumable freeConsumable = MarketGenerator.generateFreeConsumable(gameInfo,
                        next ? ConsumableType.STRENGTH : ConsumableType.DEFENSE);
                if (next) {
                    player.addStrength(freeConsumable.effect);
                }else {
                    player.addDefense(freeConsumable.effect);
                }
                stringBuilder.append("During the team repair period, ")
                        .append(player.name)
                        .append(" completed recovery training and gained ")
                        .append(freeConsumable.getEffectStr())
                        .append(next ? " strength" : "defense").append(".\n");
            }
        }

        List<Player> abandonPlayers = new ArrayList<>();
        for (Player player : gameInfo.team.players) {
            if (gameInfo.gaveUp() && !playerNums.contains(player.num)) {
                player.declineEndurance(100d);
                stringBuilder.append("During the team renovation period, ")
                        .append(player.name)
                        .append(" was in a low mood and gave up participating in the competition!")
                        .append(".\n");
                abandonPlayers.add(player);
            }
        }
        gameInfo.team.players.removeAll(abandonPlayers);
        gameInfo.team.reserves.addAll(abandonPlayers);

        String result = stringBuilder.toString();
        if (result.isEmpty()) {
            JLabel lblNewLabel = new JLabel("Nothing happened!");
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            lblNewLabel.setBounds(105, 211, 600, 55);
            add(lblNewLabel);
        } else {
            JTextArea textArea = new JTextArea(result);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(false);
            textArea.setFont(new Font("Dialog", Font.PLAIN, 18));
            JScrollPane jsp = new JScrollPane(textArea);
            jsp.setOpaque(false);
            jsp.getViewport().setOpaque(false);
            jsp.setBorder(null);
            jsp.setBounds(105, 180, 730, 150);
            add(jsp);
        }

        JButton btnNewButton = new JButton("Continuous");
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNewButton.setBounds(314, 341, 200, 50);
        btnNewButton.addActionListener(e -> {
            GameController.switchPanel(new PlaysScreen());
        });
        add(btnNewButton);

    }

}
