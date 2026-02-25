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

    
    @Override
    public boolean verify() {
        return ChefDAO.verify(this);
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

    public void recResoconto(){
        // This method should retrieve the report/resoconto for the chef.
        // Implementation depends on how reports are stored and associated with the chef.
    }

    public Report getResoconto(){
        return resoconto; 
    }

    public void aggiungiNotifica(Notifica notifica){
        // TODO Auto-generated method stub
    }

    public void eliminaNotifica(Notifica notifica){
        // TODO Auto-generated method stub
    }

    public void aggiungiCorso(Corso corso){
        // TODO Auto-generated method stub
    }

    

    
}
