package Wordle.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import Wordle.App;
import Wordle.HelpScreen;
import Wordle.LeaderboardScreen;
import Wordle.utils.Colors.TileColor;

class NavBarButton extends JButton {
    NavBarButton(String iconPath, String hoverIconPath) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        if(hoverIconPath != null) {
            ImageIcon hoverIcon = new ImageIcon(ClassLoader.getSystemResource(hoverIconPath));
            setRolloverIcon(hoverIcon);
            setPressedIcon(hoverIcon);
        }
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque( false );
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        setIcon(icon);
    }
}

public class NavBar extends JPanel {
    Container glassPane = null;
    HelpScreen helpmenu = new HelpScreen();
    LeaderboardScreen lbmenu = new LeaderboardScreen();
    public NavBar() {
        this.setPreferredSize(new Dimension(100, 50));
        this.setBackground(new Color(0x121213));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new Box.Filler(new Dimension(4, 0), new Dimension(16, 0), new Dimension(32, 0)));
        JButton helpButton = new NavBarButton("images/help.png", null);
        this.add(helpButton);
        this.add(Box.createHorizontalGlue());
        Font titleFont=new Font(Font.SERIF,Font.BOLD,36);
        JLabel title = new JLabel(new ImageIcon(ClassLoader.getSystemResource("images/logo.png")));
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        this.add(title);
        this.add(Box.createHorizontalGlue());
        JButton leaderboardButton = new NavBarButton("images/ranking.png", "images/rankinghover.png");
        this.add(leaderboardButton);
        this.add(new Box.Filler(new Dimension(4, 0), new Dimension(16, 0), new Dimension(32, 0)));
        this.setBorder(new MatteBorder(0, 0, 1, 0, new Color(TileColor.BORDER)));
        glassPane = (Container) App.frame.getGlassPane();
        glassPane.setLayout(new GridBagLayout());
        glassPane.addMouseListener(new MouseAdapter(){});
        glassPane.setVisible(false);
        helpmenu.closeButton.addActionListener((ActionEvent e)-> {
            glassPane.removeAll();
            glassPane.setVisible(false);
        });
        leaderboardButton.addActionListener((ActionEvent e)->{
            glassPane.removeAll();
            glassPane.add(lbmenu);
            glassPane.setVisible(true);
        });
        lbmenu.closeButton.addActionListener((ActionEvent e)-> {
            glassPane.removeAll();
            glassPane.setVisible(false);
        });
        helpButton.addActionListener((ActionEvent e)->{
            glassPane.removeAll();
            glassPane.add(helpmenu);
            glassPane.setVisible(true);
            helpmenu.playColorAnimations();
        });

    }

}