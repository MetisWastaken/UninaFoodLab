package com.ufl.controller;

import java.time.LocalDate;

import com.ufl.MainController;
import com.ufl.model.Online;
import com.ufl.view.AMEOnlinePanel.OnlineRow;


public class ModificheController {
    public static void createAggiungiNotificaButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getCorsoNotificaPanel().addAggiungiNotificaButtonListener(e -> {
            main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta notifica in corso!");
            main_controller.mostraAggiungiNotifica();
        });
    }

    public static void createAggiungiOnlineButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getPoBox().getOnlinePanel().addAggiungiButtonListener(e -> {
            main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta sessione online in corso!");
            main_controller.mostraAggModOnline(null);
        });
    }

    public static void createModificaOnlineButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getOnlinePanel().getOnlinePanel().getOnlineRows().forEach(selectedRow -> {
            selectedRow.addModificaButtonListener(e -> {
                main_controller.getMainframe().showInfoLog("SUCC", "Caricamento modifica sessione online in corso!");
                main_controller.mostraAggModOnline(selectedRow.getSessioneOnline());
            });
        });

    }

    public static void createEliminaOnlineButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getOnlinePanel().getOnlinePanel().getOnlineRows().forEach(selectedRow -> {
            selectedRow.addEliminaButtonListener(e -> {
                main_controller.getMainframe().showInfoLog("SUCC", "Caricamento eliminazione sessione online in corso!");
                selectedRow.getSessioneOnline().delete();
                main_controller.mostraModificaCorso();;
            });
        });
    }




    public static void createAMOnlineConfermaButtonListener(MainController main_controller) {
        main_controller.getAMOnlineFrame().addConfermaListener(e -> {
            String codice_meeting = main_controller.getAMOnlineFrame().getCodiceMeeting();
            LocalDate giorno_sessione = main_controller.getAMOnlineFrame().getGiornoSessione();

            if(codice_meeting.isEmpty() || giorno_sessione == null) {
                main_controller.getMainframe().showInfoLog("ERR", "Tutti i campi devono essere compilati!");
                return;
            }
            Online vecchia_sessione_online= main_controller.getAMOnlineFrame().getOnlineOriginale();
            if(vecchia_sessione_online == null) {
                // Aggiunta
                Online nuova_sessione_online = new Online(main_controller.getCorsoAttivo().getId(), giorno_sessione, codice_meeting);
                if(nuova_sessione_online.insert()) {
                    main_controller.getMainframe().showInfoLog("SUCC", "Sessione online aggiunta con successo!");
                    main_controller.getAMOnlineFrame().pulisciCampi();
                    main_controller.getAMOnlineFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainframe().showInfoLog("ERR", "Errore durante l'aggiunta della sessione online!");
                }
            } else {
                // Modifica
                Online nuova_sessione_online = new Online(main_controller.getCorsoAttivo().getId(), giorno_sessione, codice_meeting);
                if(vecchia_sessione_online.update(nuova_sessione_online)) {
                    main_controller.getMainframe().showInfoLog("SUCC", "Sessione online modificata con successo!");
                    main_controller.getAMOnlineFrame().pulisciCampi();
                    main_controller.getAMOnlineFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainframe().showInfoLog("ERR", "Errore durante la modifica della sessione online!");
                }
            }
        });
    }
}
