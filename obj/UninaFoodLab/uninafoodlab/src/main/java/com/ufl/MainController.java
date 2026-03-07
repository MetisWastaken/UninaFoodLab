package com.ufl;

import javax.swing.SwingUtilities;

import com.ufl.controller.LoginController;

import com.ufl.view.LoginPage;
import com.ufl.view.MainFrame;

import com.ufl.model.Chef;
import com.ufl.model.Corso;

public class MainController {
    private MainFrame main_frame;
    private LoginPage login_page;

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
        
        LoginController.createLoginListener(this);
        main_frame.setContent(login_page);
        
    }
    public void logAvvenuto() {
        System.out.println("Login effettuato con successo!");
        main_frame.setContent(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        
    }
    
    
}
