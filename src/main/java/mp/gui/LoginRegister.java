package mp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginRegister {
    private JButton loginButton = null;
    private JButton registerButton = null;
    private JTextField usernameField = null;
    private JPasswordField passwordField = null;
    private JFrame frame = null;

    public LoginRegister() {
        try {
            JLabel panel = new JLabel();

            JLabel usernameLabel = new JLabel("Username");
            usernameLabel.setBounds(100, 8, 70, 20);

            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(100, 55, 70, 20);

            this.usernameField = new JTextField();
            this.usernameField.setBounds(100, 27, 193, 28);

            this.passwordField = new JPasswordField();
            this.passwordField.setBounds(100, 75, 193, 28);

            this.loginButton = new JButton("Login");
            this.loginButton.setBounds(95, 110, 100, 25);
            this.loginButton.setForeground(Color.WHITE);
            this.loginButton.setBackground(Color.BLACK);


            this.registerButton = new JButton("Register");
            this.registerButton.setBounds(200, 110, 100, 25);
            this.registerButton.setForeground(Color.WHITE);
            this.registerButton.setBackground(Color.BLACK);

            panel.setLayout(null);
            panel.add(usernameLabel);
            panel.add(passwordLabel);
            panel.add(this.passwordField);
            panel.add(this.usernameField);
            panel.add(this.loginButton);
            panel.add(this.registerButton);


            this.frame = new JFrame();
            this.frame.setTitle("Login Page");
            this.frame.setLocation(new Point(500, 300));
            this.frame.add(panel);
            this.frame.setSize(new Dimension(400, 200));
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setResizable(false);
            this.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JTextField getUsernameField() {
        return this.usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void setLoginCallback(ActionListener actionListener) {
        this.loginButton.addActionListener(actionListener);
    }

    public void setRegisterCallback(ActionListener actionListener) {
        this.registerButton.addActionListener(actionListener);
    }
}