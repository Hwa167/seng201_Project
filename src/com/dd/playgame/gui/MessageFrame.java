package com.dd.playgame.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class MessageFrame {
	  private JFrame frmSss;

	  private String title;
	  private String txt;
	  private boolean islong;
	  private JButton jb;

	  /**
	   * Create the application.
	   */
	  public MessageFrame(String title, String txt, boolean islong, JButton jb) {
	    this.title = title;
	    this.txt = txt;
	    this.islong = islong;
	    this.jb = jb;
	    initialize();
	  }

	  /**
	   * Initialize the contents of the frame.
	   */
	  private void initialize() {
	    frmSss = new JFrame();
	    frmSss.addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosed(WindowEvent e) {
	        if (jb != null) {
	          jb.setEnabled(true);
	        }
	      }
	    });
	    frmSss.setResizable(false);
	    frmSss.setAlwaysOnTop(true);
	    frmSss.setTitle(title);
	    frmSss.setVisible(true);
	    if (jb != null) {
	      jb.setEnabled(false);
	    }
	    Dimension windowscreen = Toolkit.getDefaultToolkit().getScreenSize();
	    frmSss.setBounds((int)(windowscreen.getWidth() - 450) / 2,
	        (int)(windowscreen.getHeight() - 170) / 2,  450, 150);
	    if (islong) {
	      frmSss.setBounds((int)(windowscreen.getWidth() - 700) / 2,
	          (int)(windowscreen.getHeight() - 170) / 2,  700, 150);
	    }
	    frmSss.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frmSss.getContentPane().setLayout(null);

	    JTextArea lblNewLabel = new JTextArea(txt);
	    lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

//	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 5, 416, 65);
		  lblNewLabel.setEditable(false);
		  lblNewLabel.setCursor(null);
		  lblNewLabel.setLineWrap(true);
		  lblNewLabel.setWrapStyleWord(true);
		  lblNewLabel.setOpaque(false);
	    if (islong) {
	      lblNewLabel.setBounds(25, 5, 650, 65);
	    }
	    frmSss.getContentPane().add(lblNewLabel);

	    JButton btnNewButton = new JButton("OK");
	    btnNewButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        frmSss.dispose();
	      }
	    });
	    btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 14));
	    btnNewButton.setBounds(175, 70, 100, 25);
	    if (islong) {
	      btnNewButton.setBounds(300, 70, 100, 25);
	    }
	    frmSss.getContentPane().add(btnNewButton);
	  }
	}