package com.ufl;

import java.time.LocalDate;
import com.ufl.dao.PraticaDAO;
import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;
import com.ufl.dao.RicettaDAO;
import com.ufl.dao.OnlineDAO;
import com.ufl.model.Online;

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
        Ricetta ricetta = RicettaDAO.get(1);
        if(ricetta != null){
            System.out.println("Ricetta trovata: " + ricetta.getIdRicetta() + ", " + ricetta.getNome() + ", " + ricetta.getDescrizione());
            ricetta.recIngredienti();
            System.out.println("Ingredienti della ricetta:");
            ricetta.getIngredienti().forEach(ingrediente -> System.out.println("- " + ingrediente.getNome()));
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }

    public static void main(String[] args) {
       //testPratica();
       //testOnline();
       testRicetta();
    }
}
