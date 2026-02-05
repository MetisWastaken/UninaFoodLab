package com.ufl.model;

import java.util.ArrayList;
import java.util.List;

public class Chef extends Utente {
    private ArrayList<Report> resoconti;


    public Chef(String username, String password) {
        super(username, password, "Chef");
    }
    public Chef(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome, "Chef");
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

    public void generaReport(){
        // TODO Auto-generated method stub
    }

    public void aggiungiCorso(Corso corso){
        // TODO Auto-generated method stub
    }

    public void aggiungiNotifica(Notifica notifica){
        // TODO Auto-generated method stub
    }

    public void aggiungiNotifica(Corso corso, Notifica notifica){
        // TODO Auto-generated method stub
    }

}
