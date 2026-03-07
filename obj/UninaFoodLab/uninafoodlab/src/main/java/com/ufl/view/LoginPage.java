package com.ufl.view;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionListener;

public class LoginPage extends UiUtil.BorderedPanel {
    private JTextField username_field;
    private JPasswordField password_field;
    private JButton login_button;
    
    public LoginPage(){
        super(UiUtil.COLORE_PRIMARIO, 3, 30);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Benvenuto, Chef!");
        titleLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel usernameLabel = new JLabel("Inserisci Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.username_field = UiUtil.createInputTextField("Username", new Dimension(250, 35));
        this.username_field.setMaximumSize(new Dimension(250, 35));
        this.username_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel passwordLabel = new JLabel("Inserisci Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.password_field = createPasswordTextField(new Dimension(250, 35));
        this.password_field.setMaximumSize(new Dimension(250, 35));
        this.password_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.login_button = UiUtil.createButton("Accedi");
        this.login_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(25));
        add(usernameLabel);
        add(Box.createVerticalStrut(15));
        add(this.username_field);
        add(Box.createVerticalStrut(25));
        add(passwordLabel);
        add(Box.createVerticalStrut(15));
        add(this.password_field);
        add(Box.createVerticalStrut(25));
        add(this.login_button);
        add(Box.createVerticalGlue());
    }


    private static JPasswordField createPasswordTextField(Dimension dimension) {
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBackground(UiUtil.COLORE_SFONDO);
        passwordTextField.setForeground(UiUtil.COLORE_TESTO1);
        passwordTextField.setPreferredSize(dimension);
        passwordTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UiUtil.COLORE_PRIMARIO, UiUtil.TEXT_FIELD_BORDER_THICKNESS, true),
            BorderFactory.createEmptyBorder(UiUtil.TEXT_FIELD_PADDING_TOP, UiUtil.TEXT_FIELD_PADDING_LEFT, UiUtil.TEXT_FIELD_PADDING_BOTTOM, UiUtil.TEXT_FIELD_PADDING_RIGHT)
        ));
       
        return passwordTextField;
    }

    public void addLoginListener(ActionListener listener) {
        this.login_button.addActionListener(listener);
    }

    public String getUsername() {
        return this.username_field.getText();
    }

    public String getPassword() {
        return new String(this.password_field.getPassword());
    }
    
    public static void main(String[] args) {
        UiUtil.TestFrame frame = new UiUtil.TestFrame();
        frame.setLayout(new FlowLayout());
        frame.setBackground(UiUtil.COLORE_SFONDO);
        JPanel loginPanel = new LoginPage();
        frame.add(loginPanel);
        frame.revalidate();
    }
}