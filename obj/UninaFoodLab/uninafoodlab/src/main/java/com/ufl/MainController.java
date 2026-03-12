package com.ufl;

import javax.swing.SwingUtilities;

import com.ufl.controller.LoginController;
import com.ufl.controller.CorsiContainerController;
import com.ufl.controller.HomepageController;
import com.ufl.controller.AggiungiCorsoController;

import com.ufl.view.MainFrame;
import com.ufl.view.UiUtil;
import com.ufl.view.VisualizzaJFreeChartReport;
import com.ufl.view.LoginPage;
import com.ufl.view.HomepageContainer;
import com.ufl.view.AggiungiCorsoFrame;
import com.ufl.view.CorsiContainerPanel;
import com.ufl.view.DettagliCorsoPanel;

import com.ufl.model.Chef;
import com.ufl.model.Corso;


public class MainController {
    private MainFrame main_frame;
    private LoginPage login_page;
    private HomepageContainer homepage_container;
    private CorsiContainerPanel corsi_container_panel;
    private AggiungiCorsoFrame aggiungi_corso_frame;
    private DettagliCorsoPanel dettagli_corso_panel;

    private Chef chef_attivo = null;
    private Corso corso_attivo = null;


    public MainFrame getMainframe() {
        return main_frame;
    }

    public LoginPage getLoginPage() {
        return login_page;
    }

    public HomepageContainer getHomepageContainer() {
        return homepage_container;
    }
    
    public CorsiContainerPanel getCorsiContainerPanel() {
        return corsi_container_panel;
    }

    public AggiungiCorsoFrame getAggiungiCorsoFrame() {
        return aggiungi_corso_frame;
    }
//----------------------------------------------------
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
//---------------------------------------------

    public void CostruisciLogIn(){
        this.login_page = new LoginPage();
        LoginController.createLoginListener(this);
    }

    public void CostruisciHomepage(){
        this.homepage_container = new HomepageContainer();
        HomepageController.createHomepageExitListener(this);
        HomepageController.createMieiCorsiListener(this);
        HomepageController.createAltriCorsiListener(this);
        HomepageController.createNotificheListener(this);
        HomepageController.createReportListener(this);
    }

    public void CostruisciMieiCorsi(String filtro_categoria){
        this.corsi_container_panel = new CorsiContainerPanel(true, chef_attivo.getCorsi(true, filtro_categoria));
        CorsiContainerController.createMCSearchCatButtonListener(this);
        CorsiContainerController.createDettagliButtonListener(this);
        CorsiContainerController.createModificaButtonListener(this);
        CorsiContainerController.createAddCorsoButtonListener(this);
    }

    public void CostruisciAltriCorsi(String filtro_categoria){
        this.corsi_container_panel = new CorsiContainerPanel(false, chef_attivo.getCorsi(false, filtro_categoria));
        CorsiContainerController.createACSearchCatButtonListener(this);
        CorsiContainerController.createDettagliButtonListener(this);
    }

    public void costruisciDettagliCorso(){
        corso_attivo.recChef();
        this.dettagli_corso_panel = new DettagliCorsoPanel(corso_attivo);
        homepage_container.setContent(this.dettagli_corso_panel);
    }

    public void CostruisciAggiungiCorso(){
        if(this.aggiungi_corso_frame == null) {
            this.aggiungi_corso_frame = new AggiungiCorsoFrame();
            AggiungiCorsoController.createAggiungiCorsoListener(this);
        }
        aggiungi_corso_frame.setVisible(true);
    }
//----------------------------------------------
    public MainController() {
        this.main_frame = new MainFrame();
        CostruisciLogIn();
        main_frame.setContent(login_page);
        
    }

    
//-----------------------------------------------
    public void logAvvenuto() {
        System.out.println("Login effettuato con successo!");
        CostruisciHomepage();
        main_frame.setContent(homepage_container);
        mostraMieiCorsi();
    }

    public void logOutAvvenuto() {
        System.out.println("Logout effettuato con successo!");
        main_frame.setContent(login_page); 
    }

    public void mostraMieiCorsi(String filtro_categoria){
        System.out.println("I miei corsi filtrati per categoria: " + filtro_categoria);
        CostruisciMieiCorsi(filtro_categoria);
        homepage_container.setContent(corsi_container_panel);
    }

    public void mostraMieiCorsi(){
        mostraMieiCorsi("");
    }

    public void mostraAltriCorsi(String filtro_categoria){
        System.out.println("Altri corsi filtrati per categoria: " + filtro_categoria);
        CostruisciAltriCorsi(filtro_categoria);
        homepage_container.setContent(corsi_container_panel);
    }

    public void mostraAltriCorsi(){
        mostraAltriCorsi("");
    }

    public void mostraDettagliCorso(){
        System.out.println("Dettagli corso: " + corso_attivo.getNome());
        costruisciDettagliCorso();
        homepage_container.setContent(dettagli_corso_panel);
    }

    public void mostraModificaCorso(){
        System.out.println("Modifica corso: " + corso_attivo.getNome());
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_PRIMARIO.darker()));
    }

    public void mostraAggiungiCorso(){
        System.out.println("Aggiungi corso");
        CostruisciAggiungiCorso();
    }

    public void mostraNotifiche(){
        System.out.println("Notifiche");
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO));
    }

public void mostraReport() {
    System.out.println("Report");

    chef_attivo.recResoconto();
    if(chef_attivo.getResoconto() == null){
        homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO));
        return;
    }
    chef_attivo.getResoconto().recNumeroRicettePerPratiche();
    chef_attivo.getResoconto().calcolaStatisticheRicettePratiche();

    homepage_container.setContent(new VisualizzaJFreeChartReport(chef_attivo.getResoconto()));
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        
    }
    
    
}
