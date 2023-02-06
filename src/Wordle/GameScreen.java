package Wordle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import Wordle.components.GameEndMenu;
import Wordle.components.NavBar;
import Wordle.components.PopUpBox;
import Wordle.components.Tile;
import Wordle.utils.SQLiteConnector;
import Wordle.utils.Colors.TileColor;

public class GameScreen extends JPanel {
    SQLiteConnector conn = null;
    JFrame parentFrame = null;
    JPanel mainBody = null;
    Container glassPane = null;
    Timer animateTimer = null;
    PopUpBox errorPopupPanel = new PopUpBox("");
    GameEndMenu gameEndMenu = new GameEndMenu();
    private String answer = "CRANE";
    private final String[] winMessages = { "Genius", "Magnificent", "Impressive", "Splendid", "Great", "Phew" };
    int currRound = 0;
    int currChar = -1;
    Tile[][] tilesArr = new Tile[6][5];

    Action submitAction;
    Action placeCharAction;
    Action removeCharAction;

    GameScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        errorPopupPanel.setVisible(false);
        conn = new SQLiteConnector();
        ResultSet rWord = conn.queryDb("select name from words order by random() limit 1;");
        try {
            if (rWord.next()) {
                answer = rWord.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(answer);
        this.setLayout(new BorderLayout());
        mainBody = new JPanel();
        mainBody.setBackground(new Color(0x121213));
        mainBody.setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(6, 5, 5, 5));
        gridPanel.setMaximumSize(new Dimension(400, 300));
        gridPanel.setBackground(new Color(0x121213));
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tilesArr[i][j] = new Tile();
                gridPanel.add(tilesArr[i][j]);
            }
        }

        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(gridPanel);
        box.add(Box.createHorizontalGlue());
        mainBody.add(box, BorderLayout.CENTER);

        Box box2 = Box.createVerticalBox();
        box2.add(Box.createVerticalStrut(30));
        box2.add(errorPopupPanel);
        box2.add(Box.createVerticalStrut(10));
        box2.add(box);
        box2.add(Box.createVerticalGlue());
        InputMap IMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap AMap = this.getActionMap();
        for (char c = 'A'; c <= 'Z'; c++) {
            // upper case
            KeyStroke capital = KeyStroke.getKeyStroke("typed " + c);
            IMap.put(capital, Character.toString(c));
            AMap.put(Character.toString(c), new placeCharAction(c));

            // lower case
            KeyStroke little = KeyStroke.getKeyStroke("typed " + Character.toLowerCase(c));
            IMap.put(little, Character.toString(Character.toLowerCase(c)));
            AMap.put(Character.toString(Character.toLowerCase(c)), new placeCharAction(Character.toUpperCase(c)));
        }
        IMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "removeChar");
        AMap.put("removeChar", new removeCharAction());
        IMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
        AMap.put("submit", new SubmitAction());
        mainBody.add(box2, BorderLayout.CENTER);
        this.add(new NavBar(), BorderLayout.NORTH);
        this.add(mainBody, BorderLayout.CENTER);
        parentFrame.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        glassPane = (Container) parentFrame.getGlassPane();
        glassPane.setLayout(new GridBagLayout());
        glassPane.addMouseListener(new MouseAdapter() {
        });
        GameEndMenu gameEndPanel = new GameEndMenu();
        gameEndPanel.playAgainButton.addActionListener((ActionEvent ev) -> {
            glassPane.setVisible(false);
            resetGame();
        });
        glassPane.add(gameEndPanel);
        glassPane.setVisible(false);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void resetGame() {
        animateTimer = new Timer(50, new clearBoardAnimation());
        animateTimer.setInitialDelay(0);
        animateTimer.setRepeats(true);
        animateTimer.setCoalesce(false);
        animateTimer.start();
    }

    private class clearBoardAnimation implements ActionListener {
        private int index = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (index < 30) {
                Tile animateTile = tilesArr[index / 5][index % 5];
                if (animateTile.getBackground().getRGB() == new Color(TileColor.DEFAULT).getRGB()) {
                    animateTimer.stop();
                    currRound = 0;
                    currChar = -1;
                } else {
                    animateTile.setBackground(new Color(TileColor.DEFAULT));
                    animateTile.setBorderColor(TileColor.BORDER);
                    animateTile.setText(" ");
                    ResultSet rWord = conn.queryDb("select name from words order by random() limit 1;");

                    try {
                        if (rWord.next()) {
                            answer = rWord.getString("name");
                        }
                    } catch (SQLException err) {
                        err.printStackTrace();
                    }
                }
            } else {
                animateTimer.stop();
                currRound = 0;
                currChar = -1;
            }
            index++;
        }
    }

    public class SubmitAction extends AbstractAction {
        private Tile animateTile;
        private int animateColor;
        private Timer animateTimer;
        private int resultColors[];
        private int correctCount;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currChar == 4) {
                correctCount = 0;
                System.out.println(answer);
                // Check Round Status
                resultColors = new int[5];
                String guess = "";
                for (Tile tile : tilesArr[currRound]) {
                    guess += tile.getText();
                }
                ResultSet rWord = conn
                        .queryDb("select name from allWords where name='" + guess.toLowerCase() + "' limit 1;");
                boolean wordExists = false;
                try {
                    wordExists = rWord.next();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (!wordExists) {
                    // Shake animation
                    errorPopupPanel.setPopUpBoxMsg("Not in word list");
                    errorPopupPanel.setVisible(true);

                    Timer timer = new Timer(2000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (Tile tile : tilesArr[currRound]) {
                                tile.shake();
                            }
                            repaint();
                        }
                    });
                    timer.setRepeats(false);
                    timer.setInitialDelay(0);
                    timer.start();

                    Timer hidePopupTimer = new Timer(2000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            errorPopupPanel.setPopUpBoxMsg("");
                            errorPopupPanel.setVisible(false);
                            repaint();
                        }
                    });
                    hidePopupTimer.setRepeats(false);
                    hidePopupTimer.setInitialDelay(1500);
                    hidePopupTimer.start();
                    return;
                }

                char[] guessCopy = guess.toCharArray();
                char[] answerCopy = answer.toUpperCase().toCharArray();

                // GREEN AND BLACK
                for (int ind = 0; ind < 5; ind++) {
                    if (answerCopy[ind] == guessCopy[ind]) {
                        resultColors[ind] = TileColor.CORRECT;
                        correctCount++;
                        answerCopy[ind] = '0';
                        guessCopy[ind] = '1';
                    } else {
                        resultColors[ind] = TileColor.NOT_FOUND;
                    }
                }

                // Yellow
                for (int ind = 0; ind < 5; ind++) {
                    if (new String(answerCopy).contains(String.valueOf(guessCopy[ind]))) {
                        char srchChar = guessCopy[ind];
                        long count = new String(answerCopy).chars().filter(ch -> ch == srchChar).count();
                        if (count == 1) {
                            int i = new String(answerCopy).indexOf(guessCopy[ind]);
                            answerCopy[i] = '0';
                            resultColors[ind] = TileColor.INCORRECT;
                        } else {
                            resultColors[ind] = TileColor.INCORRECT;
                        }
                    }
                }
                animateTimer = new Timer(300, new AddColorAnimation());
                animateTimer.setInitialDelay(0);
                animateTimer.setRepeats(true);
                animateTimer.setCoalesce(false);
                animateTimer.start();
            }
        }

        class AddColorAnimation implements ActionListener {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < 5) {
                    animateTile = tilesArr[currRound][index];
                    animateColor = resultColors[index];
                    animateTile.setBackground(new Color(animateColor));
                } else {
                    animateTimer.stop();
                    if (correctCount == 5) {
                        errorPopupPanel.setPopUpBoxMsg(winMessages[currRound]);
                        errorPopupPanel.setVisible(true);
                        Timer hidePopupTimer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                errorPopupPanel.setVisible(false);
                                glassPane.setVisible(true);
                                repaint();
                            }
                        });
                        gameEndMenu.playAgainButton.setBackground(Color.BLACK);
                        hidePopupTimer.setRepeats(false);
                        hidePopupTimer.setInitialDelay(1500);
                        hidePopupTimer.start();
                        System.out.println("You Found the word");
                    } else if (currRound == 5) {
                        glassPane.setVisible(true);
                        System.out.println("You Failed To Find The Word");
                        errorPopupPanel.setPopUpBoxMsg(answer);
                        errorPopupPanel.setVisible(true);
                        Timer hidePopupTimer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                errorPopupPanel.setVisible(false);
                                glassPane.setVisible(true);
                                repaint();
                            }
                        });
                        hidePopupTimer.setRepeats(false);
                        hidePopupTimer.setInitialDelay(1500);
                        hidePopupTimer.start();
                    }
                    currRound++;
                    currChar = -1;
                }
                index++;
            }
        }
    }

    public class placeCharAction extends AbstractAction {
        public char letter;

        placeCharAction(char c) {
            letter = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currChar < 4) {
                currChar++;
                Tile currTile = tilesArr[currRound][currChar];
                currTile.popOut();
                currTile.setChar(letter);
                currTile.setBorderColor(TileColor.ACTIVE_BORDER);
            }
        }
    }

    public class removeCharAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currChar > -1) {
                Tile currTile = tilesArr[currRound][currChar];
                currTile.setChar(' ');
                currTile.setBorderColor(TileColor.BORDER);
                currChar--;
            }
        }
    }
}