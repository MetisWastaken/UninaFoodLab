package com.ufl.controller;

import com.ufl.MainController;

import com.ufl.model.Notifica;

public class NotificheController {
    public static void createAggiungiNotificaButtonListener(MainController main_controller){
        main_controller.getNotificheContainerPanel().addAggiungiNotificaButtonListener(e -> {
             main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta notifica in corso!");
            main_controller.mostraAggiungiNotifica();
        });
    }

    public static void createEliminaNotificaButtonListener(MainController main_controller){
        main_controller.getNotificheContainerPanel().getNotificheRowPanel().forEach(notifica_row_panel -> {
            notifica_row_panel.addEliminaNotificaButtonListener(e -> {
                main_controller.getMainframe().showInfoLog("SUCC", "Caricamento eliminazione notifica in corso!");
                notifica_row_panel.getNotifica().delete();
                main_controller.mostraNotifiche();
            });
        });
    }

    public static void createAggiungiNotificaListener(MainController main_controller){
        main_controller.getAggiungiNotificheFrame().addAggiungiNotificaListener(e -> {
            String titolo = main_controller.getAggiungiNotificheFrame().getTitoloNotifica();
            String messaggio = main_controller.getAggiungiNotificheFrame().getMessaggioNotifica();
            if(titolo.isEmpty() || messaggio.isEmpty()) {
                main_controller.getMainframe().showInfoLog("ERR", "Titolo e messaggio non possono essere vuoti!");
                return;
            }

            Notifica nuova_notifica = new Notifica(main_controller.getChefAttivo().getUsername(), titolo, messaggio, main_controller.getCorsoAttivo() != null, main_controller.getCorsoAttivo() != null ? main_controller.getCorsoAttivo().getId() : null);
            if(nuova_notifica.insert()) {
                main_controller.getMainframe().showInfoLog("SUCC", "Notifica aggiunta con successo!");
                main_controller.getAggiungiNotificheFrame().pulisciCampi();
                main_controller.getAggiungiNotificheFrame().setVisible(false);
                if(main_controller.getCorsoAttivo() == null) {
                    main_controller.mostraNotifiche();
                } 
            }else {
                main_controller.getMainframe().showInfoLog("ERR", "Errore durante l'aggiunta della notifica!");
            }

        });
    }
}
