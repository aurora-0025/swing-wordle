package Wordle;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class App {
        public static JFrame frame;
        public static void main(String[] args) throws Exception {
            final String LOGO_PATH = "images/icon.png";
            ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource(LOGO_PATH));
            frame = new JFrame();
            frame.setGlassPane(new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            });
            frame.setIconImage(logo.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);
            frame.setTitle("Wordle");
            frame.getContentPane().add(new Login(frame));
            frame.setVisible(true);
        }
}