package com.ufl.controller;

import com.ufl.MainController;

import com.ufl.model.Chef;


public class LoginController {
    public static void createLoginListener(MainController mainController) {
        mainController.getLoginPage().addLoginListener(e -> {
                String username = mainController.getLoginPage().getUsername();
                String password = mainController.getLoginPage().getPassword();
                System.out.println("Tentativo di login con username: " + username + " e password: " + password);
                Chef chefAutenticato =new Chef(username, password);
                if (chefAutenticato.verify()) {
                    mainController.getMainframe().showInfoLog("SUCC", "Login effettuato con successo!");
                    mainController.setChefAttivo(chefAutenticato);
                    mainController.logAvvenuto();
                } else {
                    mainController.getMainframe().showInfoLog("ERR", "Credenziali errate. Riprova.");
                }
            }
        );
    }

}
