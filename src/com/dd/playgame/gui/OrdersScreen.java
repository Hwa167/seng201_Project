package com.dd.playgame.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;

public class OrdersScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public OrdersScreen() {
		setLayout(null);
		
		JScrollPane jsp = new JScrollPane((Component) null);
		jsp.setBounds(27, 155, 740, 209);
		add(jsp);
		
		JList list = new JList();
		jsp.setViewportView(list);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnEnter.setBounds(140, 391, 220, 40);
		add(btnEnter);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnBack_1.setBounds(460, 391, 220, 40);
		add(btnBack_1);
		
		JLabel lblChoose = new JLabel("---------------------");
		lblChoose.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblChoose.setBounds(27, 76, 734, 55);
		add(lblChoose);
		
		JLabel lblWhoFirstIs = new JLabel("Who First Is:");
		lblWhoFirstIs.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblWhoFirstIs.setBounds(27, 10, 241, 40);
		add(lblWhoFirstIs);

	}

}
