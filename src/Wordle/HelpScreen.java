package Wordle;

import javax.swing.JPanel;

import Wordle.components.Tile;
import Wordle.utils.Colors.TileColor;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JLabel;

public class HelpScreen extends JPanel {
    public HelpScreen() {
        setLayout(new GridBagLayout());
        setBackground(new Color(TileColor.DEFAULT));
        Box helpContainer = Box.createVerticalBox();
        helpContainer.add(new JLabel("How To Play"));
        helpContainer.add(new JLabel("Guess the Wordle in 6 tries"));
        helpContainer.add(new JLabel("<html><ul>" +
                "<li>Each guess must be a valid 5-letter word</li>" +
                "<li>The color of the tiles will change to show how close your guess was to the word.</li>" +
                "</ul><html>"));
        helpContainer.add(new JLabel("Examples"));
        JPanel wearyJPanel = new JPanel(new GridLayout(1, 5));
        wearyJPanel.add(new Tile(new Color(TileColor.CORRECT), 25, 'W'));
        wearyJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'E'));
        wearyJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'A'));
        wearyJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'R'));
        wearyJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'Y'));
        helpContainer.add(wearyJPanel);
        helpContainer.add(new JLabel("<html><b>W</b> is in the word and in the correct spot</html>"));

        JPanel pillsJPanel = new JPanel(new GridLayout(1, 5));
        pillsJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'P'));
        pillsJPanel.add(new Tile(new Color(TileColor.INCORRECT), 25, 'I'));
        pillsJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'L'));
        pillsJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'L'));
        pillsJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'S'));
        helpContainer.add(pillsJPanel);
        helpContainer.add(new JLabel("<html><b>I</b> is in the word and in the wrong spot</html>"));

        JPanel vagueJPanel = new JPanel(new GridLayout(1, 5));
        vagueJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'V'));
        vagueJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'A'));
        vagueJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'G'));
        vagueJPanel.add(new Tile(new Color(TileColor.NOT_FOUND), 25, 'U'));
        vagueJPanel.add(new Tile(new Color(TileColor.DEFAULT), 25, 'E'));
        helpContainer.add(vagueJPanel);
        helpContainer.add(new JLabel("<html><b>I</b> is in the word in any spot</html>"));

        add(helpContainer);
    }
}
