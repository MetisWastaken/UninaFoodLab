package com.ufl;

import javax.swing.SwingUtilities;

import com.ufl.controller.LoginController;
import com.ufl.controller.HomepageController;

import com.ufl.view.MainFrame;
import com.ufl.view.UiUtil;
import com.ufl.view.LoginPage;
import com.ufl.view.HomepageContainer;

import com.ufl.model.Chef;
import com.ufl.model.Corso;

public class MainController {
    private MainFrame main_frame;
    private LoginPage login_page;
    private HomepageContainer homepage_container;

    private Chef chef_attivo = null;
    private Corso corso_attivo = null;

    public void setMainframe(MainFrame mainframe) {
        this.main_frame = mainframe;
    }

    public MainFrame getMainframe() {
        return main_frame;
    }

    public void setLoginPage(LoginPage login_page) {
        this.login_page = login_page;
    }

    public LoginPage getLoginPage() {
        return login_page;
    }

    public void setHomepageContainer(HomepageContainer homepage_container) {
        this.homepage_container = homepage_container;
    }

    public HomepageContainer getHomepageContainer() {
        return homepage_container;
    }

    public void setChefAttivo(Chef chef) {
        this.chef_attivo = chef;
    }
    public Chef getChefAttivo() {
        return chef_attivo;
    }

    public void setCorsoAttivo(Corso corso) {
        this.corso_attivo = corso;
    }

    public Corso getCorsoAttivo() {
        return corso_attivo;
    }


    public MainController() {
        this.main_frame = new MainFrame();
        this.login_page = new LoginPage();
        this.homepage_container = new HomepageContainer();

        LoginController.createLoginListener(this);
        main_frame.setContent(login_page);
        
    }

    public void logAvvenuto() {
        System.out.println("Login effettuato con successo!");
        HomepageController.createHomepageExitListener(this);
        HomepageController.createMieiCorsiListener(this);
        HomepageController.createAltriCorsiListener(this);
        HomepageController.createNotificheListener(this);
        HomepageController.createReportListener(this);
        main_frame.setContent(homepage_container); 
    }

    public void logOutAvvenuto() {
        System.out.println("Logout effettuato con successo!");
        main_frame.setContent(login_page); 
    }

    public void mostraMieiCorsi(){
        System.out.println("I miei corsi");
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_ACCENTO));
    }

    public void mostraAltriCorsi(){
        System.out.println("Altri corsi");
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_PRIMARIO));
    }

    public void mostraNotifiche(){
        System.out.println("Notifiche");
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO));
    }

    public void mostraReport(){
        System.out.println("Report");
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_PRIMARIO.darker()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        
    }
    
    
}
