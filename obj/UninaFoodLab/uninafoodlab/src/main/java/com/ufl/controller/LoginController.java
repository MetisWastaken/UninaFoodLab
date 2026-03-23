package com.ufl.controller;

import com.ufl.MainController;

import com.ufl.model.Chef;


public class LoginController {
    public static void createLoginListener(MainController main_controller) {
        main_controller.getLoginPage().addLoginListener(e -> {
                String username = main_controller.getLoginPage().getUsername();
                String password = main_controller.getLoginPage().getPassword();
                System.out.println("Tentativo di login con username: " + username + " e password: " + password);
                Chef chefAutenticato =new Chef(username, password);
                if (chefAutenticato.verify()) {
                    chefAutenticato.recNome();
                    chefAutenticato.recCognome();

                    main_controller.getMainFrame().showInfoLog("SUCC", "Ben tornato " + chefAutenticato.getNome() + " " + chefAutenticato.getCognome() + "!");
                    main_controller.setChefAttivo(chefAutenticato);
                    main_controller.logAvvenuto();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Credenziali errate. Riprova.");
                }
            }
        );
    }

}
