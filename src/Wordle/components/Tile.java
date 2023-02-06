package Wordle.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Wordle.utils.Colors.TileColor;

public class Tile extends JLabel {
    private Runnable shakeAnimation;
    private Runnable popOutAnimation;

    Font mainFont = new Font("Clear Sans", Font.BOLD, 26);
    Color color = new Color(TileColor.DEFAULT);
    int toColor = TileColor.DEFAULT;
    char guessChar;

    public Tile() {
        this.setOpaque(true);
        this.setBackground(new Color(TileColor.DEFAULT));
        this.setBorder(new LineBorder(new Color(TileColor.BORDER), 1, true));
        this.setText(String.valueOf(guessChar));
        this.setFont(mainFont);
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(50, 50));
        popOutAnimation = () -> {
            final int delay = 50;
            try {
                for (int i = 0; i < 3; i++) {
                    setBorder(new CompoundBorder(new EmptyBorder(i, i, i, i),
                            new LineBorder(new Color(TileColor.ACTIVE_BORDER), 1, true)));
                    Thread.sleep(delay);
                }
                for (int i = 3; i > 0; i--) {
                    setBorder(new CompoundBorder(new EmptyBorder(i, i, i, i),
                            new LineBorder(new Color(TileColor.ACTIVE_BORDER), 1, true)));
                    Thread.sleep(delay);
                }
                setBorder(new LineBorder(new Color(TileColor.ACTIVE_BORDER), 1, true));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        shakeAnimation = () -> {
            final int delay = 75;
            Point point = getLocation();
            for (int i = 0; i < 2; i++) {
                try {
                    moveButton(new Point(point.x + 1, point.y));
                    Thread.sleep(delay);
                    moveButton(point);
                    Thread.sleep(delay);
                    moveButton(new Point(point.x - 1, point.y));
                    Thread.sleep(delay);
                    moveButton(point);
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                moveButton(point);
            }
        };
    }

    private void moveButton(final Point p) {
        SwingUtilities.invokeLater(() -> {
            super.setLocation(p);
        });
    }

    public void shake() {
        // SwingUtilities.invokeLater(shakeAnimation);
        new Thread(this.shakeAnimation).start();
    }

    public void popOut() {
        new Thread(this.popOutAnimation).start();
    }

    public void setBorderColor(int color) {
        this.setBorder(new LineBorder(new Color(color), 1, true));
    }

    public void setChar(char c) {
        this.setText(String.valueOf(c));
    }
}
