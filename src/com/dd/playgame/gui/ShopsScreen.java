package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class ShopsScreen extends JPanel {

    /**
     * Create the panel.
     */
    public ShopsScreen() {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Shop Selection");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        lblNewLabel.setBounds(10, 10, 608, 100);
        add(lblNewLabel);

        JButton btnNewButton = new JButton("Buy Item");
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        btnNewButton.setBounds(118, 210, 216, 84);
        btnNewButton.addActionListener(e-> GameController.switchPanel(new BuysScreen()));
        add(btnNewButton);

        JButton btnSellItem = new JButton("Sell Item\r\n");
        btnSellItem.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        btnSellItem.setBounds(483, 210, 216, 84);
        btnSellItem.addActionListener(e->GameController.switchPanel(new SellsScreen()));
        add(btnSellItem);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        btnBack.setBounds(657, 442, 135, 55);
        btnBack.addActionListener(e -> GameController.switchPanel(new PlaysScreen()));
        add(btnBack);

    }

}
