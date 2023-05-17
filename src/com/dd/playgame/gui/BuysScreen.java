package com.dd.playgame.gui;

import com.dd.playgame.application.GameInfo;
import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.MarketConsumable;
import com.dd.playgame.bean.MarketPlayer;
import com.dd.playgame.gui.frame.ChoseJoinFrame;

import javax.swing.*;
import java.awt.Font;
import java.awt.Component;

public class BuysScreen extends JPanel {

    private final JLabel lblBalance;
    private final JList shopList;
    private JTextArea lblIntroduce;

    private final GameInfo gameInfo;
    /**
     * Create the panel.
     */
    public BuysScreen() {
        this.gameInfo = PlayerGameData.getGameInfo();

        setLayout(null);

        lblBalance = new JLabel("Balance   -   " + gameInfo.team.getAmountStr());
        lblBalance.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblBalance.setBounds(431, 283, 202, 40);
        add(lblBalance);

        JScrollPane jsp = new JScrollPane((Component) null);
        jsp.setBounds(73, 100, 734, 162);
        add(jsp);

        shopList = new JList(gameInfo.market.getGoodsInfo().toArray());
        shopList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Dialog", Font.PLAIN, 14));
                return label;
            }
        });
        shopList.addListSelectionListener(e -> {
            int index = shopList.getSelectedIndex();
            if (index != -1) {
                Object item = gameInfo.market.getGoods().get(index);
                if (item instanceof MarketPlayer) {
                    MarketPlayer marketPlayer = (MarketPlayer) item;
                    lblIntroduce.setText(marketPlayer.description);
                } else {
                    MarketConsumable marketConsumable = (MarketConsumable) item;
                    lblIntroduce.setText(marketConsumable.description);
                }
            }
        });
        jsp.setViewportView(shopList);

        JLabel lblItemIntroduce = new JLabel("Item Introduce");
        lblItemIntroduce.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblItemIntroduce.setBounds(73, 283, 188, 40);
        add(lblItemIntroduce);

        lblIntroduce = new JTextArea("---------------------");
        lblIntroduce.setFont(new Font("Dialog", Font.PLAIN, 18));
        lblIntroduce.setBounds(73, 333, 734, 55);
        lblIntroduce.setEditable(false);
        lblIntroduce.setCursor(null);
        lblIntroduce.setLineWrap(true);
        lblIntroduce.setWrapStyleWord(true);
        lblIntroduce.setOpaque(false);
        add(lblIntroduce);

        JButton btnbuy = new JButton("Buy");
        btnbuy.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnbuy.setBounds(643, 283, 150, 40);
        btnbuy.addActionListener(e -> {
            buy(btnbuy);
        });
        add(btnbuy);

        JButton btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnBack.setBounds(643, 398, 150, 40);
        btnBack.addActionListener(e -> GameController.switchPanel(new ShopsScreen()));
        add(btnBack);

        JLabel lblTodayItem = new JLabel("Select Items");
        lblTodayItem.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblTodayItem.setBounds(310, 10, 230, 55);
        add(lblTodayItem);

    }

    private void buy(JButton button) {
        int index = shopList.getSelectedIndex();
        if (index == -1) {
            new MessageFrame("Error", "Please choose shop!", false, button);
            return;
        }
        Object buyItem = gameInfo.market.getGoods().get(index);
        if (buyItem instanceof MarketPlayer) {
            new ChoseJoinFrame(this, (MarketPlayer) buyItem);
        } else if (buyItem instanceof MarketConsumable) {
            new MessageFrame("Tips", gameInfo.market.buyGoods((MarketConsumable) buyItem), false, button);
            refreshData();
        }
    }

    public void refreshData() {
        lblBalance.setText("Balance   -   " + gameInfo.team.getAmountStr());
        shopList.setListData(gameInfo.market.getGoodsInfo().toArray());

    }
}
