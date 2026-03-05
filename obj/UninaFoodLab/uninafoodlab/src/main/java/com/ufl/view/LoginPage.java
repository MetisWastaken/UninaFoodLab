package com.ufl.view;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    private MainFrame mainFrame; //non so perchè pianga tbh

    public LoginPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(UiUtil.COLORE_SFONDO);

        setLayout(new GridBagLayout());

        JPanel loginCard = buildLoginCard();
        add(loginCard);
    }
    private JPanel buildLoginCard() {
        JPanel card = new JPanel();
  
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); //Costruisce il quadrato contenente gli elementi del login
        card.setBackground(Color.WHITE);
        // Bordo esterno: una linea colorata 
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UiUtil.COLORE_PRIMARIO, 2, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)  // Bordo interno: spazio vuoto(padding)
        ));

        //titolo, campi di testo e bottone di login: settati al centro con i rispettivi font
        JLabel titleLabel = new JLabel("Benvenuto, Chef!");
        titleLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel usernameLabel = new JLabel("Inserisci Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Inserisci Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField passwordField = new JPasswordField(20);
        
        JButton loginButton = new JButton("Accedi");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
/********************************************************************************************************************/
//Costruttore della card graficamente immettendo gli elementi e aggiungendo spazi verticali

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(25)); 
        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(15));
        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(25));
        card.add(loginButton);

        return card;
    }
}