package com.ufl.controller;

import com.ufl.MainController;

public class HomepageController {

    public static void createHomepageExitListener(MainController main_controller){
        main_controller.getHomepageContainer().addHomepageExitButtonListener(e -> {
                main_controller.getMainFrame().showInfoLog("SUCC", "Logout effettuato con successo, arrivederci " + main_controller.getChefAttivo().getNome() + " " + main_controller.getChefAttivo().getCognome() + "!");
                main_controller.setChefAttivo(null);
                main_controller.setCorsoAttivo(null);
                main_controller.getLoginPage().pulisciCampi();
                main_controller.logOutAvvenuto();
            }
        );
    }

    public static void createMieiCorsiListener(MainController main_controller){
        main_controller.getHomepageContainer().addMieiCorsiButtonListener(e ->{
                main_controller.setCorsoAttivo(null);
                main_controller.mostraMieiCorsi();

            }
        );
    }

    public static void createAltriCorsiListener(MainController main_controller){
        main_controller.getHomepageContainer().addAltriCorsiButtonListener(e ->{
                main_controller.setCorsoAttivo(null);
                main_controller.mostraAltriCorsi();

            }
        );
    }

    public static void createNotificheListener(MainController main_controller){
        main_controller.getHomepageContainer().addNotificheButtonListener(e ->{
                main_controller.setCorsoAttivo(null);
                main_controller.mostraNotifiche();

            }
        );
    }

    public static void createReportListener(MainController main_controller){
        main_controller.getHomepageContainer().addReportButtonListener(e ->{
                main_controller.setCorsoAttivo(null);
                main_controller.mostraReport();

            }
        );
    }
}
