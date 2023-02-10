package Wordle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Wordle.db.SQLiteConnector;
import Wordle.db.SQLiteConnector.UserError;
import Wordle.model.User;
import Wordle.utils.Colors.TileColor;


public class Login extends JPanel {
    SQLiteConnector db = new SQLiteConnector();

    Login(JFrame ParentFrame){
        setBackground(new Color(0x121213));
        setLayout(new GridBagLayout());

        Box LoginContainer = Box.createVerticalBox();
        LoginContainer.setBackground(new Color(0x00000));

        LoginLabel labelUser = new LoginLabel("Username :");
        LoginLabel labelPass = new LoginLabel("Password :");

        JTextField textUser = new JTextField(20);
        textUser.setBackground(Color.LIGHT_GRAY);     
        textUser.setCaretColor(Color.BLACK);
        textUser.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 1, false), 
        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        textUser.setForeground(Color.BLACK);
        textUser.setFont(new Font("Arial", Font.BOLD, 15));
        JPasswordField textPass = new JPasswordField(20);
        textPass.setCaretColor(Color.WHITE);
        textPass.setBackground(Color.LIGHT_GRAY);
        textPass.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 1, false), 
        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        textUser.setForeground(Color.BLACK);
        textPass.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel passwordError = new JLabel("");
        passwordError.setFont(new Font("Arial", Font.BOLD, 12));
        passwordError.setForeground(Color.RED);

        JLabel usernameError = new JLabel("");
        usernameError.setFont(new Font("Arial", Font.BOLD, 12));
        usernameError.setForeground(Color.RED);


        LoginScreenButton buttonLogin = new LoginScreenButton("     Login    ");
        buttonLogin.setBackground(Color.BLACK);
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.addActionListener((ActionEvent e)-> {
            usernameError.setText("");
            passwordError.setText("");
            User user;
            try {
                String username = textUser.getText();
                String password = new String(textPass.getPassword());
                System.out.println(username);
                if(username.equals("")) {
                    if(password.equals("")) {
                        passwordError.setText("Password is empty");
                    }
                    usernameError.setText("Invalid Username");
                }
                else {
                    user = db.getUser(username, password);
                    System.out.println("User found");
                    ParentFrame.getContentPane().removeAll();
                    ParentFrame.getContentPane().add(new GameScreen(ParentFrame, user));
                    ParentFrame.revalidate();
                }
            } catch (UserError err) {
                int errCode = err.getErrorCode();
                if(errCode == UserError.INCORRECT_PASSWORD) {
                    passwordError.setText("Incorrect Password");
                }
                else if(errCode == UserError.USER_NOT_FOUND) {
                    user = db.createUser(textUser.getText(), new String(textPass.getPassword()));
                    ParentFrame.getContentPane().removeAll();
                    ParentFrame.getContentPane().add(new GameScreen(ParentFrame, user));
                    ParentFrame.revalidate();
                }
            }
        });
        LoginScreenButton buttonGuest = new LoginScreenButton("Play as Guest");
        buttonGuest.setBackground(Color.WHITE);
        buttonGuest.setForeground(Color.BLACK);
        buttonGuest.addActionListener((ActionEvent e)-> {
            User guestUser = new User("guest", "xxxtentacion");
            ParentFrame.getContentPane().removeAll();
            ParentFrame.getContentPane().add(new GameScreen(ParentFrame, guestUser));
            ParentFrame.revalidate();
        });

        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(buttonLogin);
        buttonsBox.add(Box.createHorizontalStrut(20));
        buttonsBox.add(buttonGuest);

        LoginContainer.add(leftJustify(labelUser));
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(textUser);
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(leftJustify(usernameError));
        LoginContainer.add(Box.createVerticalStrut(20));
        LoginContainer.add(leftJustify(labelPass));
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(textPass);
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(leftJustify(passwordError));
        LoginContainer.add(Box.createVerticalStrut(20));
        LoginContainer.add(buttonsBox);
        // LoginContainer.add(Box.createVerticalStrut(10));
        // LoginContainer.add(buttonGuest);

        add(LoginContainer);

        // labelUser.setBounds(50, 50, 70, 50);
        // textUser.setBounds(140, 67, 150, 20);
        // labelPass.setBounds(50, 100, 100, 50);
        // textPass.setBounds(140, 117, 150, 20);
        // buttonLogin.setBounds(100, 165, 90, 40);
        // buttonGuest.setBounds(100, 220, 190, 40);
    }
    
    private Box leftJustify( Component comp )  {
        Box  b = Box.createHorizontalBox();
        b.add( comp );
        b.add( Box.createHorizontalGlue() );
        // (Note that you could throw a lot more components
        // and struts and glue in here.)
        return b;
    }
}

class LoginLabel extends JLabel{
    LoginLabel(String txt)
    {
        this.setText(txt);
        this.setFont(new Font("Arial", Font.BOLD, 15));
        this.setForeground(Color.WHITE);
    }
}

class LoginScreenButton extends JButton {
    public LoginScreenButton(String texString) {
        this.setText(texString);
        this.setBackground(new Color(TileColor.NOT_FOUND));
        this.setForeground(Color.WHITE);
        this.setBorder(new EmptyBorder(15, 6, 15, 6));
        this.setMaximumSize(new Dimension(300, 25));
        this.setFocusable(false);
        this.setFont(new Font("Arial", Font.BOLD, 15));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        UIManager.put("Button.select", new Color(TileColor.NOT_FOUND));
    }
}