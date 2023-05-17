package com.dd.playgame.gui;

import com.dd.playgame.application.*;
import com.dd.playgame.bean.BattleResult;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BattleScreen extends JPanel {


    private static final long serialVersionUID = 1L;
    private final JButton btnBack;
    private final JButton btnBattle;
    private final JLabel lblTitle;
    private final JButton btnInventory;

    private JTextArea textArea;
    private JLabel lblMt;
    private JLabel lblOt;

    private final JPanel jp = this;

    private Team systemTeam;
    private Team userTeam;

    private int index = 0;
    private List<PlayerRole> battleRoles;
    private Optional<Player> systemPlayer;
    private Optional<Player> userPlayer;

    private List<Double[]> scores;

    /**
     * Create the panel.
     */
    public BattleScreen() {
        this.systemTeam = TeamGenerator.generateTeam(PlayerGameData.getGameInfo());
        this.userTeam = PlayerGameData.getGameInfo().team;
        this.index = 1;
        this.battleRoles = new ArrayList<>();
        this.battleRoles.addAll(Arrays.asList(PlayerRole.values()));
        this.scores = new ArrayList<>();

        setLayout(null);

        lblTitle = new JLabel("Round " + index);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblTitle.setBounds(370, 5, 217, 50);
        add(lblTitle);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 15));
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setBounds(75, 195, 730, 180);
        add(jsp);

        btnBattle = new JButton("Battle");
        btnBattle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                battle(btnBattle);
            }
        });
        btnBattle.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnBattle.setBounds(170, 398, 140, 40);
        add(btnBattle);

        btnInventory = new JButton("Inventory");
        btnInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//        GameController.switchPanel();
            }
        });
        btnInventory.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnInventory.setBounds(370, 398, 140, 40);
        btnInventory.setVisible(false);
        add(btnInventory);

        btnBack = new JButton("BACK");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameController.switchPanel(new PlaysScreen());
            }
        });
        btnBack.setFont(new Font("Dialog", Font.PLAIN, 15));
        btnBack.setBounds(570, 398, 140, 40);
        add(btnBack);

        JLabel lblTeam = new JLabel(systemTeam.name + ":");
        lblTeam.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTeam.setBounds(65, 50, 770, 30);
        add(lblTeam);

        lblMt = new JLabel("New label");
        lblMt.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblMt.setBounds(65, 75, 770, 30);
        add(lblMt);

        JLabel lblTeam2 = new JLabel(userTeam.name + ":");
        lblTeam2.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblTeam2.setBounds(65, 100, 770, 30);
        add(lblTeam2);

        lblOt = new JLabel("New label");
        lblOt.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblOt.setBounds(65, 125, 770, 30);
        add(lblOt);

        JLabel lblstatus = new JLabel("Battle record");
        lblstatus.setFont(new Font("Dialog", Font.BOLD, 15));
        lblstatus.setBounds(75, 162, 125, 30);
        add(lblstatus);

        battle(null, true);
    }

    public void battle(JButton button) {
        battle(button, false);
    }

    /**
     * the process of Fight.
     */
    public void battle(JButton button, boolean initial) {
        if (initial) {
            PlayerRole battleRole = battleRoles.get(new Random().nextInt(battleRoles.size()));
            battleRoles.remove(battleRole);

            systemPlayer = systemTeam.players.stream().filter(item -> item.role == battleRole).findFirst();
            userPlayer = userTeam.players.stream().filter(item -> item.role == battleRole).findFirst();
        } else {
            if (systemPlayer.isPresent() && userPlayer.isPresent()) {
                btnBack.setEnabled(false);
                btnBattle.setEnabled(true);

                Double[] itemScores = battlePlayer(userPlayer.get(), systemPlayer.get());
                scores.add(itemScores);

                if (battleRoles.isEmpty()) {
                    btnBattle.setEnabled(false);
                    btnBack.setEnabled(true);

                    double leftAllScore = 0d, rightAllScore = 0d;
                    for (Double[] score : scores) {
                        leftAllScore += score[0];
                        rightAllScore += score[1];
                    }
                    output("At the end of the competition, Team "+userTeam.name+" had a total score of "+formatScore(leftAllScore)+" and Team "+systemTeam.name+" had a total score of "+formatScore(rightAllScore)+".");
                    BattleResult result = PlayerGameData.matchSettlement(leftAllScore, rightAllScore);
                    if (result.isTeam1Win()) {
                        output("Congratulations on winning this game, you have earned a bonus of " + result.getTeam1MoneyStr() + " and " + result.getTeam1Points() + " points. Keep up the good work!");
                    } else {
                        output("You lost this game and earned " + result.getTeam1Points() + " points, keep it up and try again next time...");
                    }
                } else {
                    index++;
                    lblTitle.setText("Round " + index);
                    PlayerRole battleRole = battleRoles.get(new Random().nextInt(battleRoles.size()));
                    battleRoles.remove(battleRole);

                    systemPlayer = systemTeam.players.stream().filter(item -> item.role == battleRole).findFirst();
                    userPlayer = userTeam.players.stream().filter(item -> item.role == battleRole).findFirst();
                }
            } else {
                btnBack.setEnabled(true);
                btnBattle.setEnabled(false);
                new MessageFrame("Error", "Missing specified type of athlete!", false, button);
            }
        }
        lblMt.setText((systemPlayer.isPresent() ? systemPlayer.get().formatBasic() : "-------------------------"));
        lblOt.setText((userPlayer.isPresent() ? userPlayer.get().formatBasic() : "-------------------------"));
    }

    private Double[] battlePlayer(Player leftPlayer, Player rightPlayer) {
        output(leftPlayer.name + " and " + rightPlayer.name + " are competing in strength...");
        double score1 = leftPlayer.calculateScore();
        double score2 = rightPlayer.calculateScore();

        boolean win = score1 == score2 ? ThreadLocalRandom.current().nextBoolean() : score1 > score2;
        output(BattleGenerator.getGreeting(win));

        output(leftPlayer.name + " capability score of " + formatScore(score1) + ", "
                + rightPlayer.name + " capability score of " + formatScore(score2) + ".  "
                + (win ? leftPlayer.name : rightPlayer.name) + " wins.");

        output("------------------------------------------------------------------");
        double basic = 3.0;

        double scoreRatio = score1 / (score1 + score2);
        double endurance1 = basic + scoreRatio * 10;
        double endurance2 = basic + (1 - scoreRatio) * 10;
        if (win) {
            leftPlayer.declineEndurance(endurance2);
            rightPlayer.declineEndurance(endurance1);
        } else {
            leftPlayer.declineEndurance(endurance1);
            rightPlayer.declineEndurance(endurance2);
        }
        return new Double[]{score1, score2};
    }

    private String formatScore(double score) {
        return new BigDecimal(score).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * output message.
     */
    private void output(String mess) {
        textArea.setText(textArea.getText() + mess + "\r\n");
    }
}
