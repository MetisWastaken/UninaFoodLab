package com.ufl.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Studente extends Utente {
    private Map<Corso,LocalDate> date_iscrizione_corsi;

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

    public void iscrittoCorso(Corso corso) {
        date_iscrizione_corsi.put(corso, LocalDate.now());
    }

    public Map<Corso, LocalDate> getDateIscrizioneCorsi() {
        return date_iscrizione_corsi;
    }

    public void iscrittoPratica(Corso corso, Pratica pratica) {
        date_iscrizione_corsi.put(corso, LocalDate.now());
    }

    public List<Pratica> getPratiche() {
        // TODO Auto-generated method stub
        return null;
    }
}