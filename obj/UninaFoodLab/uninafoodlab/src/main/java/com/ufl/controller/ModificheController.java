package com.ufl.controller;

import java.time.LocalDate;

import com.ufl.MainController;
import com.ufl.model.Online;
import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;



public class ModificheController {
    public static void createAggiungiNotificaButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getCorsoNotificaPanel().addAggiungiNotificaButtonListener(e -> {
            main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento aggiunta notifica in corso!");
            main_controller.mostraAggiungiNotifica();
        });
    }

    public static void createAggiungiOnlineButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getPoBox().getAMEOnlinePanel().addAggiungiButtonListener(e -> {
            main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento aggiunta sessione online in corso!");
            main_controller.mostraAggModOnline(null);
        });
    }

    public static void createModificaOnlineButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getAMEOnlinePanel().getOnlinePanel().getOnlineRows().forEach(selectedRow -> {
            selectedRow.addModificaButtonListener(e -> {
                main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento modifica sessione online in corso!");
                main_controller.mostraAggModOnline(selectedRow.getSessioneOnline());
            });
        });

    }

    public static void createEliminaOnlineButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getAMEOnlinePanel().getOnlinePanel().getOnlineRows().forEach(selectedRow -> {
            selectedRow.addEliminaButtonListener(e -> {
                if(selectedRow.getSessioneOnline().delete()){
                    main_controller.getMainFrame().showInfoLog("SUCC", "Eliminazione sessione online in corso!");
                    main_controller.mostraModificaCorso();;
                }
                else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante l'eliminazione della sessione online!");
                }
                
                
            });
        });
    }

    public static void createAggiungiPraticaButtonListener(MainController main_controller) {
        main_controller.getModificaCorsoPanel().getPoBox().getAMEPratichePanel().addAggiungiButtonListener(e -> {
            main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento aggiunta sessione pratica in corso!");
            main_controller.mostraAggModPratica(null);
        });
    }

    public static void createModificaPraticaButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getAMEPratichePanel().getPraticaPanel().getPraticaRows().forEach(selectedRow -> {
            selectedRow.addModificaButtonListener(e -> {
                main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento modifica sessione pratica in corso!");
                main_controller.mostraAggModPratica(selectedRow.getSessionePratica());
            });
        });

    }

    public static void createEliminaPraticaButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getAMEPratichePanel().getPraticaPanel().getPraticaRows().forEach(selectedRow -> {
            selectedRow.addEliminaButtonListener(e -> {
                if(selectedRow.getSessionePratica().delete()){
                    main_controller.getMainFrame().showInfoLog("SUCC", "Eliminazione sessione pratica in corso!");
                    main_controller.mostraModificaCorso();;
                }
                else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante l'eliminazione della sessione pratica!");

                }
            });
        });
    }

    public static void createAggRicettaPraticaButtonListener(MainController main_controller) {

        main_controller.getModificaCorsoPanel().getPoBox().getAMEPratichePanel().getPraticaPanel().getPraticaRows().forEach(selectedRow -> {
            selectedRow.addAggRicettaButtonListener(e -> {
                main_controller.getMainFrame().showInfoLog("SUCC", "Caricamento aggiunta ricette alla pratica in corso!");
                main_controller.mostraAggRicettaPratica(selectedRow.getSessionePratica());
            });
        });
    }

    public static void createAMOnlineConfermaButtonListener(MainController main_controller) {
        main_controller.getAMOnlineFrame().addConfermaListener(e -> {
            String codice_meeting = main_controller.getAMOnlineFrame().getCodiceMeeting();
            LocalDate giorno_sessione = main_controller.getAMOnlineFrame().getGiornoSessione();

            if(codice_meeting.isEmpty() || giorno_sessione == null) {
                main_controller.getMainFrame().showInfoLog("ERR", "Tutti i campi devono essere compilati!");
                return;
            }
            Online vecchia_sessione_online= main_controller.getAMOnlineFrame().getOnlineOriginale();
            if(vecchia_sessione_online == null) {
                // Aggiunta
                Online nuova_sessione_online = new Online(main_controller.getCorsoAttivo().getId(), giorno_sessione, codice_meeting);
                if(nuova_sessione_online.insert()) {
                    main_controller.getMainFrame().showInfoLog("SUCC", "Sessione online aggiunta con successo!");
                    main_controller.getAMOnlineFrame().pulisciCampi();
                    main_controller.getAMOnlineFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante l'aggiunta della sessione online!");
                }
            } else {
                // Modifica
                Online nuova_sessione_online = new Online(main_controller.getCorsoAttivo().getId(), giorno_sessione, codice_meeting);
                if(vecchia_sessione_online.update(nuova_sessione_online)) {
                    main_controller.getMainFrame().showInfoLog("SUCC", "Sessione online modificata con successo!");
                    main_controller.getAMOnlineFrame().pulisciCampi();
                    main_controller.getAMOnlineFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante la modifica della sessione online!");
                }
            }
        });
    }

    public static void createAMPraticaConfermaButtonListener(MainController main_controller) {
        main_controller.getAMPraticaFrame().addConfermaListener(e -> {
            LocalDate giorno_sessione = main_controller.getAMPraticaFrame().getGiornoSessione();
            String aula = main_controller.getAMPraticaFrame().getAula();
            int posti_totali = main_controller.getAMPraticaFrame().getPostiTotali();

            if(aula.isEmpty() || giorno_sessione == null || posti_totali <= 0) {
                main_controller.getMainFrame().showInfoLog("ERR", "Tutti i campi devono essere compilati!");
                return;
            }
            Pratica vecchia_sessione_pratica= main_controller.getAMPraticaFrame().getPraticaOriginale();
            if(vecchia_sessione_pratica == null) {
                // Aggiunta
                Pratica nuova_sessione_pratica = new Pratica(main_controller.getCorsoAttivo().getId(), giorno_sessione, aula, posti_totali);
                if(nuova_sessione_pratica.insert()) {
                    main_controller.getMainFrame().showInfoLog("SUCC", "Sessione pratica aggiunta con successo!");
                    main_controller.getAMPraticaFrame().pulisciCampi();
                    main_controller.getAMPraticaFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante l'aggiunta della sessione pratica!");
                }
            } else {
                // Modifica
                Pratica nuova_sessione_pratica = new Pratica(main_controller.getCorsoAttivo().getId(), giorno_sessione, aula, posti_totali);
                if(vecchia_sessione_pratica.update(nuova_sessione_pratica)) {
                    main_controller.getMainFrame().showInfoLog("SUCC", "Sessione pratica modificata con successo!");
                    main_controller.getAMPraticaFrame().pulisciCampi();
                    main_controller.getAMPraticaFrame().setVisible(false);
                    main_controller.mostraModificaCorso();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante la modifica della sessione pratica!");
                }
            }
        });
    }
    public static void createRicettaRowListener(MainController main_controller) {
        if(main_controller.getAggiungiRicetteFrame() == null) return;
        main_controller.getAggiungiRicetteFrame().getRicettePanel().getRicetteRows().forEach(ricettaRow -> {
            ricettaRow.addRicettaRowListener(e -> {
                Ricetta ricetta_selezionata = ricettaRow.getRicetta();
                Pratica pratica_attiva = main_controller.getAggiungiRicetteFrame().getPraticaAttiva();

                if(pratica_attiva == null) {
                    main_controller.getMainFrame().showInfoLog("ERR", "Nessuna pratica selezionata!");
                    return;
                }

                if(pratica_attiva.aggiungiRicetta(ricetta_selezionata)) {
                    main_controller.getMainFrame().showInfoLog("SUCC", "Ricetta aggiunta alla pratica con successo!");
                    ricettaRow.disableRicettaRow();
                } else {
                    main_controller.getMainFrame().showInfoLog("ERR", "Errore durante l'aggiunta della ricetta alla pratica!");
                }
            });
        });
    }
}
