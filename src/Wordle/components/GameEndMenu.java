package Wordle.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Wordle.utils.CenterComponent;
import Wordle.utils.Colors.TileColor;

public class GameEndMenu extends JPanel {
    public JButton playAgainButton;
    public ActionListener l;
    public GameEndMenu() {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(new Color(0x1a1a1b), 1, true));
        setBackground(new Color(0x121213));
        JPanel menuPanel = new JPanel();
        JPanel statsPanel = new JPanel();
        menuPanel.setBackground(new Color(0x121213));
        statsPanel.setBackground(new Color(0x121213));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS));

        Box playedStatsBox = Box.createVerticalBox();
        playedStatsBox.setAlignmentY(TOP_ALIGNMENT);
        MenuTextLabel playedStats = new MenuTextLabel("0", 30);
        playedStatsBox.add(HCenter(playedStats));
        playedStatsBox.add(HCenter(new MenuTextLabel("Played")));
        Box winRateStatsBox = Box.createVerticalBox();
        winRateStatsBox.setAlignmentY(TOP_ALIGNMENT);
        MenuTextLabel winRateStats = new MenuTextLabel("0"+"%", 30);
        winRateStatsBox.add(HCenter(winRateStats));
        winRateStatsBox.add(HCenter(new MenuTextLabel("Win %")));
        Box currentStreakStatsBox = Box.createVerticalBox();
        currentStreakStatsBox.setAlignmentY(TOP_ALIGNMENT);
        MenuTextLabel currentStreakStats = new MenuTextLabel("0", 30);
        currentStreakStatsBox.add(HCenter(currentStreakStats));
        currentStreakStatsBox.add(HCenter(new MenuTextLabel("Current")));
        currentStreakStatsBox.add(HCenter(new MenuTextLabel("Streak")));
        Box maxStreakStatsBox = Box.createVerticalBox();
        maxStreakStatsBox.setAlignmentY(TOP_ALIGNMENT);
        MenuTextLabel maxStreakStats = new MenuTextLabel("0", 30);
        maxStreakStatsBox.add(HCenter(maxStreakStats));
        maxStreakStatsBox.add(HCenter(new MenuTextLabel("Max")));
        maxStreakStatsBox.add(HCenter(new MenuTextLabel("Streak")));
        
        statsPanel.add(Box.createHorizontalGlue());
        statsPanel.add(Box.createHorizontalStrut(30));
        statsPanel.add(playedStatsBox);
        statsPanel.add(Box.createHorizontalStrut(10));
        statsPanel.add(winRateStatsBox);
        statsPanel.add(Box.createHorizontalStrut(10));
        statsPanel.add(currentStreakStatsBox);
        statsPanel.add(Box.createHorizontalStrut(10));
        statsPanel.add(maxStreakStatsBox);
        statsPanel.add(Box.createHorizontalStrut(30));
        statsPanel.add(Box.createHorizontalGlue());

        ImageIcon replayIcon = new ImageIcon(ClassLoader.getSystemResource("images/replay.png"));

        playAgainButton = new JButton("PLAY AGAIN!", replayIcon);
        playAgainButton.setPreferredSize(new Dimension(200, 50));
        playAgainButton.setHorizontalTextPosition(JButton.LEADING);
        playAgainButton.setFocusable(false);
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playAgainButton.setBorder(new LineBorder(new Color(TileColor.CORRECT), 5, true));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBackground(new Color(TileColor.CORRECT));
        Box buttonContainer = Box.createHorizontalBox();
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(playAgainButton);
        buttonContainer.add(Box.createHorizontalGlue());

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(Box.createVerticalStrut(30));
        menuPanel.add(HCenter(new MenuTextLabel("STATISTICS", 20)));
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(new CenterComponent(statsPanel));
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(buttonContainer);
        menuPanel.add(Box.createVerticalStrut(30));
        menuPanel.add(Box.createVerticalGlue());

        add(menuPanel, BorderLayout.CENTER);
    }

    public Box HCenter(Component comp) {
        Box hCenterBox = Box.createHorizontalBox();
        hCenterBox.add(Box.createHorizontalGlue());
        hCenterBox.add(comp);
        hCenterBox.add(Box.createHorizontalGlue());
        return hCenterBox;
    }
}

class MenuTextLabel extends JLabel {
    MenuTextLabel(String text) {
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setText(text);
    }

    MenuTextLabel(String text, int size) {
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, size));
        this.setText(text);
    }
}
