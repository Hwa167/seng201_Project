package com.dd.playgame.gui.frame;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.gui.ClubsScreen;
import com.dd.playgame.gui.MessageFrame;
import com.dd.playgame.application.GameInfo;
import com.dd.playgame.bean.Consumable;
import com.dd.playgame.bean.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChoseConsumableFrame {

    private JFrame frmSss;

    private JButton btnCancel;

    private ClubsScreen clubsScreen;

    private Player player;
    private JList consumableList;

    /**
     * Create the application.
     */
    public ChoseConsumableFrame(ClubsScreen clubsScreen, Player player) {
        initialize();
        this.clubsScreen = clubsScreen;
        this.player = player;
        frmSss.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSss = new JFrame();
        frmSss.setResizable(false);
        frmSss.setAlwaysOnTop(true);
        frmSss.setTitle("Using consumables");
        Dimension windowScreen = Toolkit.getDefaultToolkit().getScreenSize();
        frmSss.setBounds((int) (windowScreen.getWidth() - 430) / 2,
                (int) (windowScreen.getHeight() - 210) / 2, 440, 250);
        frmSss.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmSss.getContentPane().setLayout(null);

        JLabel unitLabel = new JLabel("Consumables");
        unitLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        unitLabel.setBounds(35, 5, 384, 40);
        frmSss.getContentPane().add(unitLabel);

        JScrollPane jsp2 = new JScrollPane((Component) null);
        jsp2.setBounds(30, 45, 380, 120);
        frmSss.getContentPane().add(jsp2);

        List<String> showConsumables = new ArrayList<>();
        for (Consumable consumable : PlayerGameData.getGameInfo().team.consumables) {
            showConsumables.add(consumable.formatBasic());
        }
        consumableList = new JList(showConsumables.toArray());
        jsp2.setViewportView(consumableList);

        JButton btnNewButton = new JButton("Confirm");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (useConsumable(btnNewButton)) {
                    frmSss.dispose();
                    clubsScreen.refreshData();
                }
            }
        });
        btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnNewButton.setBounds(90, 180, 100, 25);
        frmSss.getContentPane().add(btnNewButton);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmSss.dispose();
            }
        });
        btnCancel.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnCancel.setBounds(240, 180, 100, 25);
        frmSss.getContentPane().add(btnCancel);
    }

    private boolean useConsumable(JButton button) {
        int index = consumableList.getSelectedIndex();
        if (index ==-1) {
            new MessageFrame("Tips", "Please select the consumables to use!", false, button);
            return false;
        }
        new MessageFrame("Tips", PlayerGameData.getGameInfo().team.useConsumable(player, index), false, button);
        return true;
    }
}
