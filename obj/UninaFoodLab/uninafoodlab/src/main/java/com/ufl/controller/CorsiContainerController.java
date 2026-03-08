package com.ufl.controller;

import com.ufl.MainController;

import com.ufl.view.CorsiContainerPanel;

import com.ufl.dao.CorsoDAO;

public class CorsiContainerController {
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
        for(CorsiContainerPanel.CorsoPanel corso_panel : main_controller.getCorsiContainerPanel().getArrayCorsiPanel()){
            corso_panel.addDettagliButtonListener(e -> {
                    Integer corso_id = corso_panel.getCorso_id();
                    main_controller.getMainframe().showInfoLog("SUCC", "Caricamento dettagli corso in corso!");
                    main_controller.mostraDettagliCorso(CorsoDAO.get(corso_id));
                }
            );
        }
    }
    
    public static void createModificaButtonListener(MainController main_controller){
        for(CorsiContainerPanel.CorsoPanel corso_panel : main_controller.getCorsiContainerPanel().getArrayCorsiPanel()){
            corso_panel.addModificaButtonListener(e -> {
                    Integer corso_id = corso_panel.getCorso_id();
                    main_controller.getMainframe().showInfoLog("SUCC", "Caricamento modifica corso in corso!");
                    main_controller.setCorsoAttivo(CorsoDAO.get(corso_id));
                    main_controller.mostraModificaCorso();
                }
            );
        }
    }

    public static void createAddCorsoButtonListener(MainController main_controller){
        main_controller.getCorsiContainerPanel().getCorsiPanel().addAddCorsoButtonListener(e->{
            main_controller.getMainframe().showInfoLog("SUCC", "Caricamento aggiunta corso in corso!");
            main_controller.mostraAggiungiCorso();
        });
    }
}
