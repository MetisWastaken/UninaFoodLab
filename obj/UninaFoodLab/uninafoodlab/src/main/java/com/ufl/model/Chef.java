package com.ufl.model;

import java.util.ArrayList;
import java.util.List;

import com.ufl.dao.UtenteDAO;
import com.ufl.dao.ChefDAO;

public class Chef extends Utente {
    private ArrayList<Report> resoconti=null;


    public Chef(String username, String password) {
        super(username, password, "Chef");
    }
    public Chef(String username, String password, String nome, String cognome) {
        super(username, password, "Chef", nome, cognome);
    }

    @Override
    public List<Notifica> getNotifiche() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Corso> getCorsi() {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<Report> getResoconti() {
        return resoconti;
    }

    public void aggiungiReport(Report report){
        // TODO Auto-generated method stub
    }

    public void aggiungiNotifica(Notifica notifica){
        // TODO Auto-generated method stub
    }

    public void aggiungiCorso(Corso corso){
        // TODO Auto-generated method stub
    }

    public void modificaCorso(Corso corso, Notifica notifica){
        // TODO Auto-generated method stub
    }

    public void eliminaCorso(Corso corso, Notifica notifica){
        // TODO Auto-generated method stub
    }

    public boolean equals(Utente utente){
        // This method should compare two Chef objects for equality.
        // Implementation depends on which attributes should be compared.
        return false; // Placeholder return statement
    }

    public void recResoconto(){
        // This method should retrieve the report/resoconto for the chef.
        // Implementation depends on how reports are stored and associated with the chef.
    }

    public Report getResoconto(){
        // This method should return the report/resoconto for the chef.
        // Implementation depends on how reports are retrieved.
        return null; // Placeholder return statement
    }
}
