package com.ufl.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Utente {
    protected String username;
    protected String password;
    protected String nome;
    protected String cognome;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Utente(String username, String password, String nome, String cognome) {
        this(username, password);
        this.nome = nome;
        this.cognome = cognome;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public List<Notifica> getNotifiche() {
        return null;
    }
    
    public ArrayList<Corso> getCorsi() {
        return null;
    }
}