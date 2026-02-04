package com.ufl.model;

import java.time.LocalDate;

public class Notifica {
    private String username_chef;
    private String titolo;
    private String messaggio;
    private boolean solo_iscritti;
    private LocalDate data_creazione;
    private int corso_id;

    public Notifica(String username_chef, String titolo, String messaggio) {
        this.username_chef = username_chef;
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.solo_iscritti = false;
        this.data_creazione = LocalDate.now();
        this.corso_id = -1;
    }
    public Notifica(String username_chef, String titolo, String messaggio, int corso_id) {
        this.username_chef = username_chef;
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.solo_iscritti = true;
        this.data_creazione = LocalDate.now();
        this.corso_id = corso_id;
    }

    public String getUsernameChef() {
        return username_chef;
    }

    public String getTitolo() {
        return titolo;
    }
    
    public String getMessaggio() {
        return messaggio;
    }

    public boolean isSoloIscritti() {
        return solo_iscritti;
    }
    public LocalDate getDataCreazione() {
        return data_creazione;
    }

    public int getCorsoId() {
        return corso_id;
    }
}   
