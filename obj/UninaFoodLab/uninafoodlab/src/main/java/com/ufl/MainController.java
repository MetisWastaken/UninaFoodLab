package com.ufl;

import javax.swing.SwingUtilities;

import com.ufl.controller.LoginController;



import com.ufl.view.LoginPage;
import com.ufl.view.MainFrame;

import com.ufl.model.Chef;
import com.ufl.model.Corso;

public class MainController {
    private MainFrame mainframe;
    private LoginPage loginPage;

    private Chef chefAttivo = null;
    private Corso corsoAttivo = null;

    public void setMainframe(MainFrame mainframe) {
        this.mainframe = mainframe;
    }

    public MainFrame getMainframe() {
        return mainframe;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public void setChefAttivo(Chef chef) {
        this.chefAttivo = chef;
    }
    public Chef getChefAttivo() {
        return chefAttivo;
    }

    public void setCorsoAttivo(Corso corso) {
        this.corsoAttivo = corso;
    }

    public Corso getCorsoAttivo() {
        return corsoAttivo;
    }

    

    public MainController() {
        this.mainframe = new MainFrame();
        this.loginPage = new LoginPage();
        LoginController.createLoginListener(this);
        mainframe.setContent(loginPage);

    }
    public void logAvvenuto() {
        System.out.println("Login effettuato con successo!");
        mainframe.setContent(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        
    }
    
    
}
