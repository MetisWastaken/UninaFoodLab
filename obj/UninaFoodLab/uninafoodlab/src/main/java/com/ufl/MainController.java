package com.ufl;

import javax.swing.SwingUtilities;

import com.ufl.controller.LoginController;
import com.ufl.controller.ModificheController;
import com.ufl.controller.NotificheController;
import com.ufl.controller.CorsiController;
import com.ufl.controller.HomepageController;


import com.ufl.view.MainFrame;
import com.ufl.view.NotificheContainerPanel;
import com.ufl.view.UiUtil;
import com.ufl.view.VisualizzaJFreeChartReport;
import com.ufl.view.LoginPage;
import com.ufl.view.HomepageContainer;
import com.ufl.view.AggiungiCorsoFrame;
import com.ufl.view.AggiungiNotificheFrame;
import com.ufl.view.AggiungiRicetteFrame;
import com.ufl.view.CorsiContainerPanel;
import com.ufl.view.DettagliCorsoPanel;
import com.ufl.view.ModificaCorsoPanel;
import com.ufl.view.AMOnlineFrame;
import com.ufl.view.AMPraticaFrame;
import com.ufl.model.Chef;
import com.ufl.model.Corso;
import com.ufl.model.Report;

import com.ufl.model.Online;
import com.ufl.model.Pratica;

public class MainController {
    private MainFrame main_frame;
    private LoginPage login_page;
    private HomepageContainer homepage_container;
    private CorsiContainerPanel corsi_container_panel;
    private AggiungiCorsoFrame aggiungi_corso_frame;
    private DettagliCorsoPanel dettagli_corso_panel;
    private NotificheContainerPanel notifiche_container_panel;
    private AggiungiNotificheFrame aggiungi_notifiche_frame;
    private ModificaCorsoPanel modifica_corso_panel;
    private AMOnlineFrame am_online_frame;
    private AMPraticaFrame am_pratica_frame;
    private AggiungiRicetteFrame aggiungi_ricette_frame;
    private VisualizzaJFreeChartReport visualizza_report;

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
    
    // getDettagliCorsoPanel non è necessario perché i controlli vengono fatti al suo interno


    public NotificheContainerPanel getNotificheContainerPanel() {
        return notifiche_container_panel;
    }

    public AggiungiNotificheFrame getAggiungiNotificheFrame() {
        return aggiungi_notifiche_frame;
    }

    public ModificaCorsoPanel getModificaCorsoPanel() {
        return modifica_corso_panel;
    }

    public AMOnlineFrame getAMOnlineFrame() {
        return am_online_frame;
    }

    public AMPraticaFrame getAMPraticaFrame() {
        return am_pratica_frame; 
    }
    public AggiungiRicetteFrame getAggiungiRicetteFrame() {
        return aggiungi_ricette_frame; 
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

    public void costruisciLogIn(){
        this.login_page = new LoginPage();
        LoginController.createLoginListener(this);
    }

    public void costruisciHomepage(){
        this.homepage_container = new HomepageContainer();
        HomepageController.createHomepageExitListener(this);
        HomepageController.createMieiCorsiListener(this);
        HomepageController.createAltriCorsiListener(this);
        HomepageController.createNotificheListener(this);
        HomepageController.createReportListener(this);
    }

    public void costruisciMieiCorsi(String filtro_categoria){
        this.corsi_container_panel = new CorsiContainerPanel(true, chef_attivo.getCorsi(true, filtro_categoria));
        CorsiController.createMCSearchCatButtonListener(this);
        CorsiController.createDettagliButtonListener(this);
        CorsiController.createModificaButtonListener(this);
        CorsiController.createAggiungiCorsoButtonListener(this);
    }

    public void costruisciAltriCorsi(String filtro_categoria){
        this.corsi_container_panel = new CorsiContainerPanel(false, chef_attivo.getCorsi(false, filtro_categoria));
        CorsiController.createACSearchCatButtonListener(this);
        CorsiController.createDettagliButtonListener(this);
    }

    public void costruisciAggiungiCorso(){
        if(this.aggiungi_corso_frame == null) {
            this.aggiungi_corso_frame = new AggiungiCorsoFrame();
            CorsiController.createAggiungiCorsoListener(this);
        }
        aggiungi_corso_frame.setVisible(true);
    }

    public void costruisciDettagliCorso(){
        corso_attivo.recChef();
        this.dettagli_corso_panel = new DettagliCorsoPanel(corso_attivo);
        
    }

    public void costruisciNotificheContainer(){
        this.notifiche_container_panel = new NotificheContainerPanel(chef_attivo.getNotifiche());
        NotificheController.createAggiungiNotificaButtonListener(this);
        NotificheController.createEliminaNotificaButtonListener(this);
        
    }

    public void costruisciAggiungiNotifica(String a_chi_si_riferisce){
        if(this.aggiungi_notifiche_frame == null) {
            this.aggiungi_notifiche_frame = new AggiungiNotificheFrame();
            NotificheController.createAggiungiNotificaListener(this);
        }
        
        aggiungi_notifiche_frame.inserisciTesto(a_chi_si_riferisce);
        aggiungi_notifiche_frame.setVisible(true);
    }

    public void costruisciModificaCorso(){
        this.modifica_corso_panel = new ModificaCorsoPanel(corso_attivo);
        ModificheController.createAggiungiNotificaButtonListener(this);
        // Per Online
        ModificheController.createAggiungiOnlineButtonListener(this);
        ModificheController.createModificaOnlineButtonListener(this);
        ModificheController.createEliminaOnlineButtonListener(this);
        // Per Pratica
        ModificheController.createAggiungiPraticaButtonListener(this);
        ModificheController.createModificaPraticaButtonListener(this);
        ModificheController.createEliminaPraticaButtonListener(this);
        ModificheController.createAggRicettaPraticaButtonListener(this);
    }

    public void costruisciAMOnlineFrame(Online online) {
        if(this.am_online_frame == null) {
            this.am_online_frame = new AMOnlineFrame();
        }
        am_online_frame.setAMOnlineFrame(online);
        ModificheController.createAMOnlineConfermaButtonListener(this);
        am_online_frame.setVisible(true);
    }

    public void costruisciAMPraticaFrame(Pratica pratica) {
        if(this.am_pratica_frame == null) {
            this.am_pratica_frame = new AMPraticaFrame();
        }
        am_pratica_frame.setAMPraticaFrame(pratica);
        ModificheController.createAMPraticaConfermaButtonListener(this);
        am_pratica_frame.setVisible(true);
    }

    public void costruisciAggiungiRicetteFrame(Pratica pratica) {
        if(this.aggiungi_ricette_frame == null) {
            this.aggiungi_ricette_frame = new AggiungiRicetteFrame();
        }
        aggiungi_ricette_frame.setAggiungiRicetteFrame(pratica);
        ModificheController.createRicettaRowListener(this);
        aggiungi_ricette_frame.setVisible(true);
    }

    public void costruisciJFreeChartReport(){
        chef_attivo.recResoconto();
        if(chef_attivo.getResoconto() == null){
            visualizza_report = null;
            return;
        }
        chef_attivo.getResoconto().recNumeroRicettePerPratiche();
        chef_attivo.getResoconto().calcolaStatisticheRicettePratiche();
        visualizza_report = new VisualizzaJFreeChartReport(chef_attivo.getResoconto());
    }
//----------------------------------------------
    public MainController() {
        this.main_frame = new MainFrame();
        costruisciLogIn();
        main_frame.setContent(login_page);
        
    }
//-----------------------------------------------
    public void logAvvenuto() {
        System.out.println("Login effettuato con successo!");
        costruisciHomepage();
        main_frame.setContent(homepage_container);
        mostraMieiCorsi();
    }

    public void logOutAvvenuto() {
        System.out.println("Logout effettuato con successo!");
        main_frame.setContent(login_page); 
    }

    public void mostraMieiCorsi(String filtro_categoria){
        System.out.println("I miei corsi filtrati per categoria: " + filtro_categoria);
        costruisciMieiCorsi(filtro_categoria);
        homepage_container.setContent(corsi_container_panel);
    }

    public void mostraMieiCorsi(){
        mostraMieiCorsi("");
    }

    public void mostraAltriCorsi(String filtro_categoria){
        System.out.println("Altri corsi filtrati per categoria: " + filtro_categoria);
        costruisciAltriCorsi(filtro_categoria);
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
        costruisciModificaCorso();
        homepage_container.setContent(modifica_corso_panel);
    }

    public void mostraAggiungiCorso(){
        System.out.println("Aggiungi corso");
        costruisciAggiungiCorso();
    }

    public void mostraNotifiche(){
        System.out.println("Notifiche");
        costruisciNotificheContainer();
        homepage_container.setContent(this.notifiche_container_panel);
    }

    public void mostraAggiungiNotifica(){
        System.out.println("Aggiungi notifica");
        costruisciAggiungiNotifica(this.corso_attivo != null ? "ai partecipanti di/del " + corso_attivo.getNome() : "a tutti");
    }

    public void mostraAggModOnline(Online online) {
        System.out.println((online == null ? "Aggiungi" : "Modifica") + " sessione online");
        costruisciAMOnlineFrame(online);
    }

    public void mostraAggModPratica(Pratica pratica) {
        System.out.println((pratica == null ? "Aggiungi" : "Modifica") + " sessione pratica");
        costruisciAMPraticaFrame(pratica);
    }

    public void mostraAggRicettaPratica(Pratica pratica) {
        System.out.println("Aggiungi ricette alla pratica del " + UiUtil.DATE_FORMATTER.format(pratica.getGiornoSessione()));
        costruisciAggiungiRicetteFrame(pratica);
    }

    public void mostraReport() {
        System.out.println("Report");

        costruisciJFreeChartReport();
        if (visualizza_report == null) {
            homepage_container.setContent(new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO));
            return;
        }
        homepage_container.setContent(visualizza_report);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        
    }
    
    
}
