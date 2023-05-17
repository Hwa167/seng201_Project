package com.dd.playgame.gui;

import javax.swing.*;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainScreen() {
		setLayout(null);

		JButton btnNewButton = new JButton("Play\r\n");
		btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          GameController.switchPanel(new SecondScreen());
	        }
	      });
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(34, 411, 216, 52);
		add(btnNewButton);

		JButton btnAbout = new JButton("About\r\n");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Author: Xiaojiang Song & Hao Wang");
			}
		});
		btnAbout.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		btnAbout.setBackground(Color.WHITE);
		btnAbout.setBounds(310, 411, 216, 52);
		add(btnAbout);

		JButton btnQuit = new JButton("Quit\r\n");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		btnQuit.setBackground(Color.WHITE);
		btnQuit.setBounds(598, 411, 216, 52);
		add(btnQuit);
	}

}
