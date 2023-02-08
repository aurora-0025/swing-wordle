package Wordle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import java.awt.Color;
import java.awt.GridBagLayout;
import Wordle.components.MyButton;


public class Login extends JPanel {
    /**
     * 
     */
    Login(){

        setBackground(new Color(0x121213));
        setLayout(new GridBagLayout());

        JPanel LoginContainer = new JPanel();
        LoginContainer.setLayout(new BoxLayout(LoginContainer,BoxLayout.Y_AXIS));
        LoginContainer.setBackground(new Color(0x121213));

        LoginLabel labelUser = new LoginLabel("Username :");
        LoginLabel labelPass = new LoginLabel("Password :");

        JTextField textUser = new JTextField(20);
        final var textPass = new JPasswordField(20);


        MyButton buttonLogin = new MyButton("Login");
        MyButton buttonGuest = new MyButton("Play as Guest");

        LoginContainer.add(labelUser);
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(textUser);
        LoginContainer.add(Box.createVerticalStrut(30));
        LoginContainer.add(labelPass);
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(textPass);
        LoginContainer.add(Box.createVerticalStrut(30));
        LoginContainer.add(buttonLogin);
        LoginContainer.add(Box.createVerticalStrut(10));
        LoginContainer.add(buttonGuest);

        add(LoginContainer);

        // labelUser.setBounds(50, 50, 70, 50);
        // textUser.setBounds(140, 67, 150, 20);
        // labelPass.setBounds(50, 100, 100, 50);
        // textPass.setBounds(140, 117, 150, 20);
        // buttonLogin.setBounds(100, 165, 90, 40);
        // buttonGuest.setBounds(100, 220, 190, 40);


    }
}

class LoginLabel extends JLabel{
    LoginLabel(String txt)
    {
        this.setText(txt);
        this.setForeground(Color.WHITE);
    }
}

