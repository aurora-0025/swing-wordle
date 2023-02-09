package Wordle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class App {
        public JFrame frame;
        public static void main(String[] args) throws Exception {
            final String LOGO_PATH = "images/icon.png";
            ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource(LOGO_PATH));
            JFrame frame = new JFrame();
            frame.setIconImage(logo.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);
            frame.setTitle("Wordle");
            frame.getContentPane().add(new Login(frame));
            frame.setVisible(true);
        }
}