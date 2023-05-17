package com.dd.playgame.gui.frame;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.Consumable;
import com.dd.playgame.bean.GameInfo;
import com.dd.playgame.bean.Player;
import com.dd.playgame.bean.Team;
import com.dd.playgame.gui.MessageFrame;
import com.dd.playgame.gui.StoresScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * When using consumables, select the target athlete
 */
public class ChosePlayerFrame {

    private JFrame frmSss;

    private JButton btnCancel;

    private StoresScreen storesScreen;

    private Consumable consumable;
    private JList playerList;

    private Team userTeam;

    /**
     * Create the application.
     */
    public ChosePlayerFrame(StoresScreen storesScreen, Consumable consumable) {
        this.storesScreen = storesScreen;
        this.consumable = consumable;
        this.userTeam = PlayerGameData.getGameInfo().team;

        initialize();
        frmSss.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSss = new JFrame();
        frmSss.setResizable(false);
        frmSss.setAlwaysOnTop(true);
        frmSss.setTitle("Chose Player");
        Dimension windowScreen = Toolkit.getDefaultToolkit().getScreenSize();
        frmSss.setBounds((int) (windowScreen.getWidth() - 530) / 2,
                (int) (windowScreen.getHeight() - 340) / 2, 520, 310);
        frmSss.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmSss.getContentPane().setLayout(null);

        JLabel unitLabel = new JLabel("All Players");
        unitLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        unitLabel.setBounds(35, 5, 384, 40);
        frmSss.getContentPane().add(unitLabel);

        JScrollPane jsp2 = new JScrollPane((Component) null);
        jsp2.setBounds(30, 45, 460, 180);
        frmSss.getContentPane().add(jsp2);

        List<String> showPlayers = new ArrayList<>();
        for (Player item : userTeam.players) {
            showPlayers.add(item.formatBasic());
        }
        for (Player item : userTeam.reserves) {
            showPlayers.add(item.formatBasic());
        }
        playerList = new JList(showPlayers.toArray());
        jsp2.setViewportView(playerList);

        JButton btnNewButton = new JButton("Confirm");
        btnNewButton.addActionListener(e -> {
            if (useConsumable(btnNewButton)) {
                frmSss.dispose();
                storesScreen.refreshData();
            }
        });
        btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnNewButton.setBounds(120, 240, 100, 25);
        frmSss.getContentPane().add(btnNewButton);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frmSss.dispose());
        btnCancel.setFont(new Font("Dialog", Font.PLAIN, 14));
        btnCancel.setBounds(270, 240, 100, 25);
        frmSss.getContentPane().add(btnCancel);
    }

    /**
     * Use consumables for selected athletes
     *
     * @param button
     * @return
     */
    private boolean useConsumable(JButton button) {
        int index = playerList.getSelectedIndex();
        if (index == -1) {
            new MessageFrame("Tips", "Please select the player to use!", false, button);
            return false;
        }
        Player itemPlayer;
        if (index < userTeam.players.size()) {
            itemPlayer = userTeam.players.get(index);
        } else {
            itemPlayer = userTeam.reserves.get(index - userTeam.players.size());
        }
        new MessageFrame("Tips", PlayerGameData.getGameInfo().team.useConsumable(itemPlayer, consumable), false, button);
        return true;
    }
}
