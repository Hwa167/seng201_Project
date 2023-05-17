package com.dd.playgame.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JList;

public class UsesScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public UsesScreen() {
		setLayout(null);
		
		JLabel lblName = new JLabel("You will use <dynamic>");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblName.setBounds(58, 319, 413, 56);
		add(lblName);
		
		JLabel lblUseItems = new JLabel("Use Items");
		lblUseItems.setHorizontalAlignment(SwingConstants.CENTER);
		lblUseItems.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblUseItems.setBounds(58, 10, 740, 56);
		add(lblUseItems);
		
		JScrollPane jsp = new JScrollPane((Component) null);
		jsp.setBounds(58, 93, 740, 216);
		add(jsp);
		
		JList list = new JList();
		jsp.setViewportView(list);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnEnter.setBackground(Color.WHITE);
		btnEnter.setBounds(681, 319, 117, 42);
		add(btnEnter);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(681, 431, 117, 42);
		add(btnBack);

	}
}
