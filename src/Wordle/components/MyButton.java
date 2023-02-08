package Wordle.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import Wordle.utils.Colors.TileColor;

public class MyButton extends JButton {
    public MyButton(String texString) {
        this.setText(texString);
        this.setBackground(new Color(TileColor.NOT_FOUND));
        this.setBorder(new LineBorder(new Color(TileColor.BORDER), 5, true));
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(200, 50));
        this.setFocusable(false);
        this.setFont(new Font("Arial", Font.BOLD, 15));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
