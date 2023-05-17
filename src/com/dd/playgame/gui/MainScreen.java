package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;

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
		
		JLabel lblNewLabel_1 = new JLabel("Sports Tournament");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1.setBounds(34, 216, 449, 148);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainScreen.class.getResource("/Image/kung-fu.png")));
		lblNewLabel.setBounds(10, 10, 860, 492);
		add(lblNewLabel);
	}
}
