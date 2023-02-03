package Wordle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class App {
        public static void main(String[] args) throws Exception {
            final String LOGO_PATH = "images/icon.png";
            ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource(LOGO_PATH));
            JFrame frame = new JFrame();

            frame.setIconImage(logo.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(600, 600);
            frame.setTitle("Wordle");
            frame.setContentPane(new GameScreen());
            frame.setVisible(true);
        }
}