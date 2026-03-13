package com.ufl.controller;

import com.ufl.MainController;

import com.ufl.view.ModificaCorsoPanel;

public class ModificheController {
    public static void createAggiungiNotificaButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getCorsoNotificaPanel().addAggiungiNotificaButtonListener(e -> {
        
            main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta notifica in corso!");
            main_controller.mostraAggiungiNotifica();
        });
    }

}
