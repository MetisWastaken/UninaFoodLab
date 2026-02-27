package com.ufl;


import com.ufl.dao.PraticaDAO;
import com.ufl.model.Pratica;

import com.ufl.dao.OnlineDAO;
import com.ufl.model.Online;

import com.ufl.model.Ricetta;
import com.ufl.dao.RicettaDAO;

import com.ufl.model.Chef;
import com.ufl.dao.ChefDAO;

import com.ufl.model.Report;
import com.ufl.dao.ReportDAO;

import com.ufl.model.Notifica;
import com.ufl.dao.NotificaDAO;

import com.ufl.model.Corso;
import com.ufl.dao.CorsoDAO;
    
public class Main {

    public static void testPratica() {
        Pratica pratica = PraticaDAO.get(2);
        if (pratica != null) {
            System.out.println("Pratica trovata: " + pratica.getCorsoId() + ", " + pratica.getGiornoSessione() + ", " + pratica.getAula() + ", " + pratica.getPostiTotali());
            System.out.println("Studenti iscritti: " + pratica.getStudentiIscritti());
            pratica.recRicette();
            System.out.println("Ricette associate alla pratica:");
            pratica.getRicette().forEach(ricetta -> System.out.println("- " + ricetta.getNome()));
            pratica.aggiungiRicetta(RicettaDAO.get(2));
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
            chef.aggiungiNotifica(new Notifica("GEsposito", "Palle2","Belle palle fratello",false,java.time.LocalDate.now(),null));
            chef.getNotifiche().forEach(notifica -> {
                System.out.println("Notifica: " + notifica.getTitolo() + " - " + notifica.getMessaggio() +" (Data: " + notifica.getDataCreazione() + ", Corso: " + notifica.getCorsoId() + ")");
            });
        } else {
            System.out.println("Verifica chef fallita.");
        }
        
    }

    public static void main(String[] args) {
       //testPratica();
       //testOnline();
       //testRicetta();
        testChef();
    }
}
