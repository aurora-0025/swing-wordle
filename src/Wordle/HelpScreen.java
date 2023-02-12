package Wordle;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import Wordle.components.Tile;
import Wordle.utils.Colors.TileColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HelpScreen extends JPanel {
    private Timer animateTimer;
    int[] wearyColors = {
        TileColor.CORRECT,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
    };
    Tile[] wearyTiles = {
        new Tile(25, 'W'),
        new Tile(25, 'E'),
        new Tile(25, 'A'),
        new Tile(25, 'R'),
        new Tile(25, 'Y'),
    };
    int[] pillsColors = {
        TileColor.NOT_FOUND,
        TileColor.INCORRECT,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
    };
    Tile[] pillsTiles = {
        new Tile(25, 'P'),
        new Tile(25, 'I'),
        new Tile(25, 'L'),
        new Tile(25, 'L'),
        new Tile(25, 'S'),
    };
    int[] vagueColors = {
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
        TileColor.NOT_FOUND,
    };
    Tile[] vagueTiles = {
        new Tile(25, 'V'),
        new Tile(25, 'A'),
        new Tile(25, 'G'),
        new Tile(25, 'U'),
        new Tile(25, 'E'),
    };

    Tile[][] allTiles = {wearyTiles, pillsTiles, vagueTiles};
    int[][] allTileColors = {wearyColors, pillsColors, vagueColors}; 
    public JButton closeButton = new JButton("X");

    public HelpScreen() {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(new Color(0x1a1a1b), 1, true));
        setBackground(new Color(0x121213));
        Box hPaddingBox = Box.createHorizontalBox();

        JPanel helpContainer = new JPanel();
        closeButton.setFocusable(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setOpaque( false );
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setFont(new Font("Clear Sans", Font.PLAIN, 16));
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(Color.WHITE);

        Box closeButtonContainer = Box.createHorizontalBox();
        closeButtonContainer.add(Box.createHorizontalGlue());
        closeButtonContainer.add(closeButton);
        closeButtonContainer.add(Box.createHorizontalStrut(10));

        helpContainer.setLayout(new BoxLayout(helpContainer, BoxLayout.Y_AXIS));
        helpContainer.setOpaque(false);
        helpContainer.add(closeButtonContainer);
        helpContainer.add(leftJustify(new HelpText("<html> <font face='Times New Roman'><b>How To Play</b></font></html>", 26)));
        helpContainer.add(Box.createVerticalStrut(5));
        helpContainer.add(leftJustify(new HelpText("<html> <font face='Times New Roman'>Guess the Wordle in 6 tries.</font></html>", 22)));

        JLabel points = new HelpText("<html><ul compact>" +
        "<li>Each guess must be a valid 5-letter word</li>" +
        "<li>The color of the tiles will change to show<br>how close your guess was to the word.</li>" +
        "</ul><html>");

        helpContainer.add(leftJustify(points));
        helpContainer.add(leftJustify(new HelpText("<html><b>Examples</b></html>")));
        helpContainer.add(Box.createVerticalStrut(10));
        JPanel wearyJPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        wearyJPanel.setOpaque(false);
        wearyJPanel.setMaximumSize(new Dimension(300, 40));
        for (Tile wTile : wearyTiles) {
            wearyJPanel.add(wTile);
        }
        helpContainer.add(leftJustify(wearyJPanel));
        helpContainer.add(Box.createVerticalStrut(10));
        helpContainer.add(leftJustify(new HelpText("<html><b>W</b> is in the word and in the correct spot</html>")));
        helpContainer.add(Box.createVerticalStrut(20));

        JPanel pillsJPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        pillsJPanel.setMaximumSize(new Dimension(300, 40));
        pillsJPanel.setOpaque(false);
        for (Tile pTile : pillsTiles) {
            pillsJPanel.add(pTile);
        }
        helpContainer.add(leftJustify(pillsJPanel));
        helpContainer.add(Box.createVerticalStrut(10));
        helpContainer.add(leftJustify(new HelpText("<html><b>I</b> is in the word and in the wrong spot</html>")));
        helpContainer.add(Box.createVerticalStrut(20));

        JPanel vagueJPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        vagueJPanel.setMaximumSize(new Dimension(300, 40));
        vagueJPanel.setOpaque(false);
        for (Tile vTile : vagueTiles) {
            vagueJPanel.add(vTile);
        }
        helpContainer.add(leftJustify(vagueJPanel));
        helpContainer.add(Box.createVerticalStrut(10));
        helpContainer.add(leftJustify(new HelpText("<html><b>U</b> is not in the word in any spot</html>")));
        helpContainer.add(Box.createVerticalStrut(20));
        helpContainer.add(new JSeparator());
        helpContainer.add(Box.createVerticalStrut(20));
        helpContainer.add(leftJustify(new HelpText("<html><font color='#477aaa'>Log in</font> to link your stats</html>")));
        helpContainer.add(Box.createVerticalStrut(20));
        helpContainer.add(new JSeparator());
        helpContainer.add(Box.createVerticalStrut(20));
        helpContainer.add(leftJustify(new HelpText("<html>Unlimited wordle games all day long!</html>")));
    

        Box vPaddingBox = Box.createVerticalBox();

        hPaddingBox.add(Box.createHorizontalStrut(30));
        hPaddingBox.add(helpContainer);
        hPaddingBox.add(Box.createHorizontalStrut(30));

        vPaddingBox.add(Box.createVerticalStrut(30));
        vPaddingBox.add(hPaddingBox);
        vPaddingBox.add(Box.createVerticalStrut(30));

        add(vPaddingBox, BorderLayout.CENTER);
    }

    private Box leftJustify(Component comp) {
        Box b = Box.createHorizontalBox();
        b.add(comp);
        b.add(Box.createHorizontalGlue());
        // (Note that you could throw a lot more components
        // and struts and glue in here.)
        return b;
    }

    private class HelpText extends JLabel {
        HelpText(String text) {
            setText(text);
            setFont(new Font("Clear Sans", Font.BOLD, 12));
            setForeground(Color.WHITE);
        }

        HelpText(String text, int size) {
            setText(text);
            setFont(new Font("Clear Sans", Font.PLAIN, size));
            setForeground(Color.WHITE);
        }
    }

    public void playColorAnimations() {
        animateTimer = new Timer(150, new AddColorAnimation(allTiles, allTileColors));
        animateTimer.setInitialDelay(0);
        animateTimer.setRepeats(true);
        animateTimer.setCoalesce(false);
        animateTimer.start();
    }

    class AddColorAnimation implements ActionListener {
        private int index = 0;
        private int[][] resultColorsArr;
        private Tile[][] tilesArr;

        AddColorAnimation(Tile[][] tilesArr, int[][] resultColorsArr) {
            this.tilesArr = tilesArr;
            this.resultColorsArr = resultColorsArr;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (index < 5) {
                for (int i = 0; i < allTileColors.length; i++) {
                    Tile animateTile = tilesArr[i][index];
                    int animateColor = resultColorsArr[i][index];
                    animateTile.setBackground(new Color(animateColor));
                    animateTile.setBorder(new LineBorder(new Color(TileColor.ACTIVE_BORDER), 1, true));
                }
            } else {
                animateTimer.stop();
            }
            index++;
        }
    }
}
