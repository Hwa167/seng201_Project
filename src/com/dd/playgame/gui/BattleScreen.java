package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.BattleResult;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.PlayerRole;
import com.dd.playgame.bean.Team;
import com.dd.playgame.util.BattlePromptUtils;
import com.dd.playgame.util.RandomUtils;
import com.dd.playgame.generator.TeamGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.*;

public class BattleScreen extends JPanel {


    private static final long serialVersionUID = 1L;
    private final JButton btnBack;
    private final JButton btnBattle;
    private final JLabel lblTitle;
    private final JButton btnInventory;

    private JTextArea BattleRecordText;
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

    private boolean isMatch = false;

    /**
     * Create the panel.
     */
    public BattleScreen() {
        this(TeamGenerator.generateTeam(PlayerGameData.getGameInfo()));
        this.isMatch = true;
    }

    public BattleScreen(Team systemTeam) {
        this.systemTeam = systemTeam;
        this.userTeam = PlayerGameData.getGameInfo().team;
        this.index = 1;
        this.battleRoles = new ArrayList<>(Arrays.asList(PlayerRole.values()));
        this.scores = new ArrayList<>();
        this.isMatch  = false;

        setLayout(null);

        lblTitle = new JLabel("Round " + index);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblTitle.setBounds(370, 5, 217, 50);
        add(lblTitle);

        BattleRecordText = new JTextArea();
        BattleRecordText.setEditable(false);
        BattleRecordText.setLineWrap(true);
        BattleRecordText.setWrapStyleWord(true);
        BattleRecordText.setFont(new Font("Dialog", Font.PLAIN, 15));
        JScrollPane jsp = new JScrollPane(BattleRecordText);
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

        JLabel lblFaceTeam = new JLabel(systemTeam.name + ":");
        lblFaceTeam.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblFaceTeam.setBounds(65, 50, 770, 30);
        add(lblFaceTeam);

        lblMt = new JLabel("New label");
        lblMt.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblMt.setBounds(65, 75, 770, 30);
        add(lblMt);

        JLabel lblMyTeam = new JLabel(userTeam.name + ":");
        lblMyTeam.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblMyTeam.setBounds(65, 100, 770, 30);
        add(lblMyTeam);

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
     * the process of battle.
     */
    public void battle(JButton button, boolean initial) {
        if (initial) {
            PlayerRole battleRole = battleRoles.get(RandomUtils.getRandomInt(battleRoles.size()-1));
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
                    BattleResult result = PlayerGameData.matchSettlement(leftAllScore, rightAllScore, isMatch);
                    if (result.isTeam1Win()) {
                        output("Congratulations on winning this game, you have earned a bonus of " + result.getTeam1MoneyStr() + " and " + result.getTeam1Points() + " points. Keep up the good work!");
                    } else {
                        if(isMatch){
                            output("You lost this game and earned " + result.getTeam1Points() + " points, keep it up and try again next time...");
                        }else {
                            output("You lost this game, keep it up and try again next time...");
                        }
                    }
                } else {
                    index++;
                    lblTitle.setText("Round " + index);
                    PlayerRole battleRole = battleRoles.get(RandomUtils.getRandomInt(battleRoles.size()-1));
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

    /**
     * Individual athletes compete in abilities
     *
     * @param leftPlayer
     * @param rightPlayer
     * @return
     */
    private Double[] battlePlayer(Player leftPlayer, Player rightPlayer) {
        output(leftPlayer.name + " and " + rightPlayer.name + " are competing in strength...");
        double score1 = leftPlayer.calculateScore();
        double score2 = rightPlayer.calculateScore();

        //At the same score, randomly obtain one side as the winning side
        boolean win = score1 == score2 ? RandomUtils.getRandomBoolean() : score1 > score2;
        output(BattlePromptUtils.getGreeting(win));

        output(leftPlayer.name + " capability score of " + formatScore(score1) + ", "
                + rightPlayer.name + " capability score of " + formatScore(score2) + ".  "
                + (win ? leftPlayer.name : rightPlayer.name) + " wins.");

        output("------------------------------------------------------------------");
        double basic = 3.0;

        //Calculate the endurance value to be reduced
        double scoreRatio = score1 / (score1 + score2);
        double endurance1 = basic + scoreRatio * 10;
        double endurance2 = basic + (1 - scoreRatio) * 10;
        if (win) {
            leftPlayer.declineEndurance(endurance2);
//            rightPlayer.declineEndurance(endurance1);
        } else {
            leftPlayer.declineEndurance(endurance1);
//            rightPlayer.declineEndurance(endurance2);
        }
        return new Double[]{score1, score2};
    }

    /**
     * Formatting scores for display
     *
     * @param score score
     * @return  score string
     */
    private String formatScore(double score) {
        return new BigDecimal(score).setScale(2, RoundingMode.FLOOR).toPlainString();
    }

    /**
     * output message.
     */
    private void output(String mess) {
        BattleRecordText.setText(BattleRecordText.getText() + mess + "\r\n");
    }
}
