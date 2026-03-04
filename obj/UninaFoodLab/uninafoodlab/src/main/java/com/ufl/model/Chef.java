package com.ufl.model;

import java.util.ArrayList;
import java.util.List;

import com.ufl.dao.ChefDAO;

public class Chef extends Utente {
    private Report resoconto=null;


    public Chef(String username, String password) {
        super(username, password);
    }
    public Chef(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome);
    }

    // --- Getters e Recs ---

        public Report getResoconto(){
        return resoconto; 
    }
    
    public List<Notifica> getNotifiche() {
        return ChefDAO.getNotifiche(this);
    }

    public void recResoconto(){
        resoconto = ChefDAO.recResoconto(this);
    }

    //--- Metodi ---

    public ArrayList<Corso> getCorsi(boolean mieiCorsi, String filtroCategoria) {
        return ChefDAO.getCorsi(this, mieiCorsi, filtroCategoria);
    }

    public void aggiungiNotifica(Notifica notifica){
        notifica.insert();
    }

    public void eliminaNotifica(Notifica notifica){
        notifica.delete();
    }

    public void aggiungiCorso(Corso corso){
        corso.insert();
    }

    public boolean verify() {
        return ChefDAO.verify(this);
    }

    

    
}
