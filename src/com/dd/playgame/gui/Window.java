package com.dd.playgame.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;




public class Window {

  private JFrame jframe;

  /**
   * Currently active panel.
   */
  private JPanel currentPanel;

  /**
   * Create the application window with the given manager.
   */
  public Window() {
    initialize();
    jframe.setVisible(true);
    Rectangle bounds = jframe.getBounds();
    bounds.height += jframe.getInsets().top + jframe.getInsets().bottom;
    bounds.width += jframe.getInsets().left + jframe.getInsets().right;
    jframe.setBounds(bounds);
  }
  
  /**
   * Switch to the given panel, removing the previous one.
   * @param panel Panel to switch to
   */
  public void switchPanel(JPanel panel) {
    if (currentPanel != null) {
      jframe.remove(currentPanel);
    }
    jframe.getContentPane().add(panel, BorderLayout.CENTER);
    panel.setVisible(true);
    currentPanel = panel;
    jframe.revalidate();
    jframe.repaint();
  }
  
  /**
   * Close the window.
   */
  public void close() {
    jframe.dispose();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    jframe = new JFrame();
    jframe.setResizable(false);
    jframe.setTitle("Taekwondo competition");

    Dimension windowScreen = Toolkit.getDefaultToolkit().getScreenSize();
    jframe.setBounds((int)(windowScreen.getWidth() - 880) / 2,
        (int)(windowScreen.getHeight() - 550) / 2, 880, 495);
    jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jframe.getContentPane().setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
  }

  /**
   * Get the application frame.
   * @return Frame.
   */
  public JFrame getFrame() {
    return jframe;
  }

}

