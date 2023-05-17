package com.dd.playgame.gui;

import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.PlayerGameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Initialization interface.
   */
  public GameOverScreen() {
    setLayout(null);

    final GameInfo gameInfo =PlayerGameData.getGameInfo();

    JLabel lblgameover = new JLabel("Game Over");
    lblgameover.setHorizontalAlignment(SwingConstants.CENTER);
    lblgameover.setFont(new Font("Dialog", Font.BOLD, 40));
    lblgameover.setBounds(290, 45, 300, 40);
    add(lblgameover);

    JLabel lblname = new JLabel("Team name: " + gameInfo.team.name);
    lblname.setHorizontalAlignment(SwingConstants.CENTER);
    lblname.setFont(new Font("Dialog", Font.PLAIN, 20));
    lblname.setBounds(290, 190, 300, 30);
    add(lblname);

    JLabel lblDifficulty = new JLabel("Difficulty: " + gameInfo.difficulty);
    lblDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
    lblDifficulty.setFont(new Font("Dialog", Font.PLAIN, 20));
    lblDifficulty.setBounds(290, 230, 300, 30);
    add(lblDifficulty);

    JLabel lblship = new JLabel("Score: " + gameInfo.team.integral);
    lblship.setHorizontalAlignment(SwingConstants.CENTER);
    lblship.setFont(new Font("Dialog", Font.PLAIN, 20));

    lblship.setBounds(290, 270, 300, 30);
    add(lblship);

    JLabel lblday = new JLabel("Weeks   -   "
        + gameInfo.cycle + " / " + gameInfo.allCycle);

    lblday.setHorizontalAlignment(SwingConstants.CENTER);
    lblday.setFont(new Font("Dialog", Font.PLAIN, 20));
    lblday.setBounds(290, 310, 300, 30);
    add(lblday);

    JLabel lblmoney = new JLabel("Balance: " + gameInfo.team.getAmountStr());
    lblmoney.setHorizontalAlignment(SwingConstants.CENTER);
    lblmoney.setFont(new Font("Dialog", Font.PLAIN, 20));
    lblmoney.setBounds(290, 350, 300, 30);
    add(lblmoney);

    JButton btnNewButton = new JButton("Quit");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameController.quit();
      }
    });
    btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 20));
    btnNewButton.setBounds(365, 400, 150, 40);
    add(btnNewButton);

    JLabel lblname2 = new JLabel(
        "This season, we have played a total of "+gameInfo.team.battleCount+" games and won "+gameInfo.team.winCount+" games.");
    lblname2.setHorizontalAlignment(SwingConstants.CENTER);
    lblname2.setFont(new Font("Dialog", Font.PLAIN, 20));
    lblname2.setBounds(90, 130, 700, 30);
    add(lblname2);

    JSeparator separator = new JSeparator();
    separator.setBounds(90, 205, 700, 2);
    add(separator);
  }
}
