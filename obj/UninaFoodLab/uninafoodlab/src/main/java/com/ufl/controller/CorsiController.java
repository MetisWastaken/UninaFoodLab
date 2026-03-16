package com.ufl.controller;

import java.time.LocalDate;

import com.ufl.MainController;


import com.ufl.view.CorsiContainerPanel.CorsoRow;

import com.ufl.model.Corso;

public class CorsiController {
    public static void createMCSearchCatButtonListener(MainController main_controller){
        main_controller.getCorsiContainerPanel().addSearchCatButtonListener(e -> {
                String filtro_categoria = main_controller.getCorsiContainerPanel().getSearchCatText();
                main_controller.getMainframe().showInfoLog("SUCC", "Ricerca dei corsi in corso!");
                main_controller.mostraMieiCorsi(filtro_categoria);
            }
        );

    }

    public static void createACSearchCatButtonListener(MainController main_controller){
        main_controller.getCorsiContainerPanel().addSearchCatButtonListener(e -> {
                String filtro_categoria = main_controller.getCorsiContainerPanel().getSearchCatText();
                main_controller.getMainframe().showInfoLog("SUCC", "Ricerca dei corsi in corso!");
                main_controller.mostraAltriCorsi(filtro_categoria);
            }
        );
    
    }

    public static void createDettagliButtonListener(MainController main_controller){
        for(CorsoRow corso_panel : main_controller.getCorsiContainerPanel().getArrayCorsiRows()){
            corso_panel.addDettagliButtonListener(e -> {
                    main_controller.getMainframe().showInfoLog("SUCC", "Caricamento dettagli corso in corso!");
                    main_controller.setCorsoAttivo(corso_panel.getCorso());
                    main_controller.mostraDettagliCorso();
                }
            );
        }
    }
    
    public static void createModificaButtonListener(MainController main_controller){
        for(CorsoRow corso_panel : main_controller.getCorsiContainerPanel().getArrayCorsiRows()){
            corso_panel.addModificaButtonListener(e -> {
                    main_controller.getMainframe().showInfoLog("SUCC", "Caricamento modifica corso in corso!");
                    main_controller.setCorsoAttivo(corso_panel.getCorso());
                    main_controller.mostraModificaCorso();
                }
            );
        }
    }

    public static void createAggiungiCorsoButtonListener(MainController main_controller){
        main_controller.getCorsiContainerPanel().getCorsiPanel().addAggiungiCorsoButtonListener(e->{
            main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta corso in corso!");
            main_controller.mostraAggiungiCorso();
        });
    }

    public static void createAggiungiCorsoListener(MainController mainController) {
        mainController.getAggiungiCorsoFrame().addAggiungiCorsoListener(e -> {
            String nome = mainController.getAggiungiCorsoFrame().getNomeCorso();
            String categoria = mainController.getAggiungiCorsoFrame().getCategoriaCorso();
            LocalDate data_in = mainController.getAggiungiCorsoFrame().getDataInizioCorso();
            LocalDate data_fin = mainController.getAggiungiCorsoFrame().getDataFineCorso();
            String frequenza = mainController.getAggiungiCorsoFrame().getFrequenzaCorso();
            
            // Validazione
            if (data_in == null || data_fin == null) {
                mainController.getMainframe().showInfoLog("ERR", "Date non valide! Usa il formato gg/mm/aaaa");
                return;
            }
            
            Corso nuovo_corso = new Corso(nome, categoria, data_in, data_fin, frequenza, mainController.getChefAttivo());
            if(nuovo_corso.insert()) {
                mainController.getMainframe().showInfoLog("SUCC", "Corso aggiunto con successo!");
                mainController.mostraMieiCorsi("");
                mainController.getAggiungiCorsoFrame().pulisciCampi();
                mainController.getAggiungiCorsoFrame().setVisible(false);
            } else {
                mainController.getMainframe().showInfoLog("ERR", "Errore durante l'aggiunta del corso!");
            }
        });
    }
}
