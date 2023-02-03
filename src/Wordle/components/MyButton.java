package Wordle.components;

import java.awt.Color;
import javax.swing.JButton;
import Wordle.utils.Colors.TileColor;

public class MyButton extends JButton {
    public MyButton(String texString) {
        setText(texString);
        setBackground(new Color(TileColor.INCORRECT));
    }
}
