package com.ufl;

import java.time.LocalDate;
import java.awt.*;
import javax.swing.*;


import com.ufl.dao.PraticaDAO;
import com.ufl.model.Pratica;

import com.ufl.dao.OnlineDAO;
import com.ufl.model.Online;

import com.ufl.model.Ricetta;
import com.ufl.dao.RicettaDAO;

import com.ufl.model.Chef;

import com.ufl.model.Notifica;

import com.ufl.model.Corso;
import com.ufl.dao.CorsoDAO;
import com.ufl.view.HomepageContainer;
import com.ufl.view.LoginPage;
import com.ufl.view.MainFrame;

    
public class MainTest {

    public static void testPratica(){
        Pratica pratica = PraticaDAO.get(2);
        if (pratica != null) {
            System.out.println("Pratica trovata: " + pratica.getCorsoId() + ", " + pratica.getGiornoSessione() + ", " + pratica.getAula() + ", " + pratica.getPostiTotali());
            System.out.println("Numero di studenti iscritti: " + pratica.getNStudentiIscritti());
            System.out.println("Studenti iscritti: " + pratica.getStudentiIscritti());
            System.out.println("Ingredienti necessari per la pratica:");
            pratica.getIngredientiPratica().forEach(ingrediente -> System.out.println("- " + ingrediente.getNome() + " (" + ingrediente.getQuantita() + " " + ingrediente.getUnitMisura() + ")"));
            pratica.recRicette();
            System.out.println("Ricette associate alla pratica:");
            pratica.getRicette().forEach(ricetta -> System.out.println("- " + ricetta.getNome()));
            //pratica.aggiungiRicetta(RicettaDAO.get(2));
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }

    public static void testOnline(){
        Online online = OnlineDAO.get(2);
        if(online != null){
            System.out.println("Online trovato: " + online.getCorsoId() + ", " + online.getGiornoSessione() + ", " + online.getCodiceMeeting());
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }

    public static void testRicetta(){
        Ricetta ricetta = RicettaDAO.get(2);
        if(ricetta != null){
            System.out.println("Ricetta trovata: " + ricetta.getIdRicetta() + ", " + ricetta.getNome() + ", " + ricetta.getDescrizione());
            ricetta.recIngredienti();
            System.out.println("Ingredienti della ricetta:");
            ricetta.getIngredienti().forEach(ingrediente -> System.out.println("- " + ingrediente.getNome()));
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }

    public static void testChef(){
        Chef chef =  new Chef("GEsposito", "pizza123");  
        if(chef.verify()){
            chef.recNome();
            chef.recCognome();
            System.out.println("Benvenuto, " + chef.getNome() + " " + chef.getCognome());
            chef.recResoconto();
            System.out.println("Resoconto dello chef: ");
            System.out.println("Numero di Corsi totali: " + chef.getResoconto().getNumeroCorsiTotali());
            System.out.println("Numero di Sessioni Online: " + chef.getResoconto().getNumeroSessioniOnline());
            System.out.println("Numero di Sessioni Pratiche: " + chef.getResoconto().getNumeroSessioniPratiche());
            chef.getResoconto().recNumeroRicettePerPratiche();
            System.out.println("Numero di ricette associate a sessioni pratiche: ");
            chef.getResoconto().getNumeroRicettePerPratiche().forEach((pratica, numeroRicette) -> {
                System.out.println("Pratica del " + pratica.getGiornoSessione() + " in aula " + pratica.getAula() + ": " + numeroRicette + " ricette");
            });
            
            chef.aggiungiNotifica(new Notifica("GEsposito", "Palle","Belle palle fratello",false,LocalDate.now(),null));
            chef.aggiungiNotifica(new Notifica("GEsposito", "Male Male","Le tue palle puzzano",true,LocalDate.now(),2));
            chef.getNotifiche().forEach(notifica -> {
                notifica.recMessagio();
                notifica.recDataCreazione();
                notifica.recSoloIscritti();
                System.out.println("Notifica: " + notifica.getTitolo() + " - " + notifica.getMessaggio() +" (Data: " + notifica.getDataCreazione() + ", Corso: " + notifica.getCorsoId() + ")");
                System.out.println("Chi può ricevere la notifica: " + notifica.getUsernameRiceventi());
                notifica.delete();
            });
            chef.getCorsi(true,"Ita").forEach(corso -> {
                System.out.println("Corso: " + corso.getNome() + " - " + corso.getCategoria() + " (Data inizio: " + corso.getDataIn() + ", Data fine: " + corso.getDataFin() + ", Frequenza: " + corso.getFrequenzaSettimanale() +")");
            });
        } else {
            System.out.println("Verifica chef fallita.");
        }
        
    }

    public static void testCorso(){
        Corso corso = CorsoDAO.get(2);
        if(corso != null){
            System.out.println("Corso trovato: " + corso.getNome() + ", " + corso.getCategoria() + ", " + corso.getDataIn() + ", " + corso.getDataFin() + ", " + corso.getFrequenzaSettimanale());
            corso.recChef();
            System.out.println("Chi lo ha creato: " + corso.getChef().getNome()+ " " + corso.getChef().getCognome());
            corso.recSessioniPratiche();
            System.out.println("\nSessioni pratiche del corso:");
            corso.getSessioniPratiche().forEach(pratica -> {
                System.out.println("- Pratica del " + pratica.getGiornoSessione() + " in aula " + pratica.getAula() + " con " + pratica.getPostiTotali() + " posti totali");
                pratica.recRicette();
                System.out.println("  Ricette associate alla pratica:");
                pratica.getRicette().forEach(ricetta -> System.out.println("  - " + ricetta.getNome()));
            });
            corso.recSessioniOnline();
            corso.modificaSessione(OnlineDAO.get(2), new Online(1, LocalDate.now().plusDays(143), "MEET12346"), new Notifica("GEsposito", "Modifica Sessione1","Palle meeting",true,LocalDate.now(),2));
            System.out.println("\nSessioni online del corso:");
            corso.getSessioniOnline().forEach(online -> {
                System.out.println("- Online del " + online.getGiornoSessione() + " con codice meeting: " + online.getCodiceMeeting());
            });
            System.out.println("Studenti iscritti al corso: " + corso.getStudentiIscritti());
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }
    public static void testLoginPage(){
        MainFrame mainframe = new MainFrame();
        LoginPage loginPage = new LoginPage();
        
        loginPage.addLoginListener(e -> {
            String username = loginPage.getUsername();
            String password = loginPage.getPassword();
            System.out.println("Tentativo di login con username: " + username + " e password: " + password);
             
            if (new Chef(loginPage.getUsername(), loginPage.getPassword()).verify()) {
                mainframe.showInfoLog("SUCC", "Login effettuato con successo!");
                // Dopo il login, puoi caricare il contenuto principale o fare altre operazioni
            } else {
                mainframe.showInfoLog("ERR", "Credenziali errate. Riprova.");
            }
        });
        mainframe.setContentCentered(loginPage);
    }

    public static void testHomepageContainer(){
        MainFrame mainframe = new MainFrame();
        HomepageContainer homepageContainer = new HomepageContainer();
        mainframe.setContent(homepageContainer);
    }
    
    public static void main(String[] args) {
        //testPratica();
        //testOnline();
        //testRicetta();
        //testChef();
        //testCorso();
        
        //testLoginPage();
        testHomepageContainer();
    }
}
