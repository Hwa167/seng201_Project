package com.dd.playgame.gui.frame;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.gui.BuysScreen;
import com.dd.playgame.gui.MessageFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Choose the unit you want to join
 */
public class ChoseJoinFrame {

    private JFrame frmSss;

    private JTextField textField;
    private JButton btnCancel;

    private String newName = "";

    private int choseUnit = 1;

    private BuysScreen buysScreen;

    private MarketPlayer buyItem;


    /**
     * Create the application.
     */
    public ChoseJoinFrame(BuysScreen buysScreen, MarketPlayer buyItem) {
        initialize();
        this.buysScreen = buysScreen;
        this.buyItem = buyItem;
        frmSss.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSss = new JFrame();
        frmSss.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MessageFrame("Tips", PlayerGameData.getGameInfo().market.buyGoods(buyItem, newName, choseUnit), false, null);
                buysScreen.refreshData();
            }
        });
        frmSss.setResizable(false);
        frmSss.setAlwaysOnTop(true);
        frmSss.setTitle("Choose to join the team");
        Dimension windowScreen = Toolkit.getDefaultToolkit().getScreenSize();
        frmSss.setBounds((int) (windowScreen.getWidth() - 430) / 2,
                (int) (windowScreen.getHeight() - 210) / 2, 440, 230);
        frmSss.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmSss.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Your player name (Enter directly as the default name):");
        lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblNewLabel.setBounds(28, 5, 384, 45);
        frmSss.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(28, 50, 350, 25);
        frmSss.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel unitLabel = new JLabel("Please choose to join the team or substitute:");
        unitLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        unitLabel.setBounds(28, 70, 384, 60);
        frmSss.getContentPane().add(unitLabel);

        JRadioButton radioButton1 = new JRadioButton("in players", true);
        radioButton1.setFont(new Font("Dialog", Font.PLAIN, 15));
        radioButton1.setBounds(35, 105, 120, 50);
        radioButton1.addActionListener(e -> {
            this.choseUnit = 1;
        });
        JRadioButton radioButton2 = new JRadioButton("in reserves");
        radioButton2.setFont(new Font("Dialog", Font.PLAIN, 15));
        radioButton2.setBounds(169, 105, 120, 50);
        radioButton2.addActionListener(e -> {
            this.choseUnit = 2;
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        frmSss.getContentPane().add(radioButton1);
        frmSss.getContentPane().add(radioButton2);

        JButton btnNewButton = new JButton("Confirm");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkName(btnNewButton)) {
                    frmSss.dispose();
                }
            }
        });
        btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnNewButton.setBounds(90, 160, 100, 25);
        frmSss.getContentPane().add(btnNewButton);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmSss.dispose();
            }
        });
        btnCancel.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnCancel.setBounds(240, 160, 100, 25);
        frmSss.getContentPane().add(btnCancel);
    }

    /**
     * Check if the entered name matches the rules
     *
     * @param button button
     * @return
     */
    private boolean checkName(JButton button) {
        String playerName = textField.getText();
        if (playerName.length() != 0) {
            if (playerName.length() < 3 || playerName.length() > 15) {
                new MessageFrame("Error", "Player name must be between 3 and 15 characters!", false, button);
                return false;
            }
            if (!playerName.matches("[a-zA-Z ]+")) {
                new MessageFrame("Error", "Name can only include letters!", false, button);
                return false;
            }
        }
        newName = playerName;
        return true;
    }
}
