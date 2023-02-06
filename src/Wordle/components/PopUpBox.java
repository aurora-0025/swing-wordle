package Wordle.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PopUpBox extends JPanel {
    JLabel msgLabel = null;
    public PopUpBox(String msg) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(300, 40));
        setBorder(new LineBorder(Color.WHITE, 2, true));
        msgLabel = new JLabel(msg);
        msgLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(msgLabel);
    }

    public void setPopUpBoxMsg(String msg) {
        msgLabel.setText(msg);
    }

    public void setVisible(boolean flag) {
        if(!flag) {
            msgLabel.setText("");
            setBackground(new Color(0, 0, 0, 0));
            setBorder(new EmptyBorder(0, 0, 0, 0));
        }
        else {
            setBackground(Color.WHITE);
            setBorder(new LineBorder(Color.WHITE, 2, true));
        }
    }
}