package Wordle;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class HelpScreen extends JPanel {
    HelpScreen() {
        add(new JLabel("How To Play"));
        add(new JLabel("Guess the Wordle in 6 tries"));
        add(new JLabel("<html><ul>" +
        "<li>Bananas are yellow</li>" +             
        "<li>Oranges are orange</li>" + 
        "<li>Strewberries are red</li>" + 
        "</ul><html>"));
    }
}
