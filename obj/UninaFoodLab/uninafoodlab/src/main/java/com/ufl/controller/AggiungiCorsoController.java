package com.ufl.controller;

import java.time.LocalDate;

import com.ufl.MainController;
import com.ufl.model.Corso;
public class AggiungiCorsoController {
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
