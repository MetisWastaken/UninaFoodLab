package com.ufl.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Studente extends Utente {
    private Map<Integer,LocalDate> date_iscrizione_corsi;

    public Studente(String username, String password) {
        super(username, password, "Studente");
    }
    public Studente(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome, "Studente");
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

    public void iscrittoCorso(int id_corso) {
        date_iscrizione_corsi.put(id_corso, LocalDate.now());
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