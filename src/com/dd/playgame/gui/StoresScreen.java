package com.dd.playgame.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JList;

public class StoresScreen extends JPanel {

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
		
		JList list = new JList();
		jsp.setViewportView(list);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnEnter.setBackground(Color.WHITE);
		btnEnter.setBounds(204, 406, 117, 42);
		add(btnEnter);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(506, 406, 117, 42);
		add(btnBack);

	}

}
