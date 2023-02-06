package Wordle.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JPanel;

public class CenterComponent extends JPanel {
    public CenterComponent(Component comp) {
        setLayout(new BorderLayout());
        setBackground(Color.CYAN);
        Box hCenterBox = Box.createHorizontalBox();
        hCenterBox.add(Box.createHorizontalGlue());
        hCenterBox.add(comp);
        hCenterBox.add(Box.createHorizontalGlue());
        Box vCenterBox = Box.createVerticalBox();
        vCenterBox.add(Box.createVerticalGlue());
        vCenterBox.add(hCenterBox);
        vCenterBox.add(Box.createVerticalGlue());
        add(vCenterBox, BorderLayout.CENTER);
    }
}
