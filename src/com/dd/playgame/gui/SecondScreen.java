package com.dd.playgame.gui;

import com.dd.playgame.application.GameController;
import com.dd.playgame.bean.Difficulty;
import com.dd.playgame.application.PlayerGameData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SecondScreen extends JPanel {
    private JTextField teamNameField;
    private Difficulty difficulty;
    private ArrayList<JButton> typeList = new ArrayList<>();
    private JTextField nickNameField;
    private JSlider slider;
    private JList flist;


    public SecondScreen() {
        setLayout(null);

        JLabel Data = new JLabel("Select Data\r\n");
        Data.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Data.setBounds(24, 39, 103, 58);
        add(Data);

        JLabel Name = new JLabel("Your Team Name: \r\n");
        Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Name.setBounds(23, 134, 174, 45);
        add(Name);

        teamNameField = new JTextField();
        teamNameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        teamNameField.setBounds(195, 139, 184, 35);
        add(teamNameField);

        JLabel Diff = new JLabel("Difficulty:\r\n\r\n");
        Diff.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Diff.setBounds(23, 223, 174, 45);
        add(Diff);

        JButton Easy = new JButton("EASY\r\n");
        Easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                selectType(Difficulty.EASY, Easy);
            }
        });
        Easy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Easy.setBounds(196, 227, 120, 36);
        Easy.setFocusable(false);
        add(Easy);
        typeList.add(Easy);

        JButton btnNormal = new JButton("NORMAL\r\n");
        btnNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                selectType(Difficulty.NORMAL, btnNormal);
            }
        });
        btnNormal.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNormal.setBounds(368, 227, 120, 36);
        btnNormal.setFocusable(false);
        add(btnNormal);
        typeList.add(btnNormal);

        JButton btnHard = new JButton("HARD\r\n");
        btnHard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ar) {
                selectType(Difficulty.HARD, btnHard);
            }
        });
        btnHard.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnHard.setBounds(530, 227, 120, 36);
        btnHard.setFocusable(false);
        add(btnHard);
        typeList.add(btnHard);


        JLabel SelectDays = new JLabel("Select Weeks (5-15):");
        SelectDays.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        SelectDays.setBounds(23, 309, 174, 45);
        add(SelectDays);

        //配置俱乐部初始运动员
//        JLabel lblNewLabel = new JLabel("Choose Athletes:");
//        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        lblNewLabel.setBounds(23, 295, 147, 45);
//        add(lblNewLabel);

//        flist = new JList(OrderTeam.getFirstList().toArray());  // 读取运动员的列表
//        flist = new JList();
//        flist.setFont(new Font("Dialog", Font.PLAIN, 17));
//        JScrollPane jsp = new JScrollPane(flist);
//        jsp.setBounds(170, 290, 600, 100);
//        add(jsp);


        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playGame(btnNext);
            }
        });
        btnNext.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnNext.setBounds(615, 52, 139, 45);
        add(btnNext);

        JLabel lblNewLabel_1 = new JLabel("5");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblNewLabel_1.setBounds(676, 309, 58, 45);
        add(lblNewLabel_1);


        slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                lblNewLabel_1.setText(Integer.toString(slider.getValue()));
            }
        });
        slider.setValue(10);
        slider.setMinimum(5);
        slider.setMaximum(15);
        slider.setBounds(211, 319, 454, 35);
        add(slider);


//        JLabel lblNewLabel_2 = new JLabel("Nickname: ");
//        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        lblNewLabel_2.setBounds(23, 431, 147, 29);
//        add(lblNewLabel_2);
//
//        nickNameField = new JTextField();
//        nickNameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        nickNameField.setBounds(183, 431, 196, 27);
//        add(nickNameField);
    }


    private void selectType(Difficulty type, JButton selectedButton) {
        difficulty = type;
        for (JButton button : typeList) {
            button.setEnabled(true);
        }
        selectedButton.setEnabled(false);
    }


    private void playGame(JButton jButton) {
        String teamName = teamNameField.getText();
        if (teamName.length() < 3 || teamName.length() > 15) {
            new MessageFrame("Error", "The name must be between 3 and 15 characters!", false, jButton);
            return;
        }
        if (!teamName.matches("[a-zA-Z ]+")) {
            new MessageFrame("Error", "The name cannot include special characters!", false, jButton);
            return;
        }
        if (difficulty == null) {
            new MessageFrame("Error", "Please select a difficulty!", false, jButton);
            return;
        }
        int weeks = slider.getValue();

//        int num = flist.getSelectedIndex() + 1;
//        if (num < 1 || num > OrderTeam.getFirstElfList().size()) {
//            new MessageFrame("Tips", "Please choose your partner", false, jButton);
//            return;
//        }

//        String nickName = nickNameField.getText();
//
//        if (!nickName.equals("")) {
//            if (nickName.length() > 2 && nickName.length() < 16) {
//                new MessageFrame("Error", "The name must be between 3 and 15 characters!", false, jButton);
//                return;
//            }
//            if (!nickName.matches("[a-zA-Z ]+")) {
//                new MessageFrame("Error", "The name can only include letters!", false, jButton);
//                return;
//            }
//        }

        //Initialize game data based on user input information
        PlayerGameData.initConfig(difficulty, teamName, weeks);
        GameController.switchPanel(new PlaysScreen());
    }
}
