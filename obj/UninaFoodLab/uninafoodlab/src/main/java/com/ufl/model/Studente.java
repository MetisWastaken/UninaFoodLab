package com.ufl.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.ufl.dao.StudenteDAO;

public class Studente extends Utente {
    private Map<Integer,LocalDate> date_iscrizione_corsi=null;

    public Studente(String username, String password) {
        super(username, password, "Studente");
    }
    public Studente(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome, "Studente");
    }

    @Override
    public boolean verify() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Notifica> getNotifiche() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Corso> getCorsi() {
        return StudenteDAO.getCorsi(this.username);
    }

    public void iscrittoCorso(int id_corso) {
        date_iscrizione_corsi.put(id_corso, LocalDate.now());
    }

    public void recIscrizioneCorsi(){
        //TODO Auto-generated method stub
    }

    public Map<Integer, LocalDate> getDateIscrizioneCorsi() {
        return date_iscrizione_corsi;
    }

    public void iscrittoPratica(Integer id_corso, LocalDate giorno_sessione) {
        // TODO Auto-generated method stub
    }
    
    //metodo da modificare o controllare in futuro
    public List<Pratica> getPratiche() {
        // TODO Auto-generated method stub
        return null;
    }
}