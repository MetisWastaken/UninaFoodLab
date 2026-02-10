package com.ufl.model;

import java.util.ArrayList;
import java.util.List;
import com.ufl.dao.UtenteDAO;

public abstract class Utente {
    protected String username;
    protected String password;
    protected String nome;
    protected String cognome;
    protected String tipo;

    public Utente(String username, String password, String tipo) {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public Utente(String username, String password, String nome, String cognome, String tipo) {
        this(username, password, tipo);
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
    public String getTipo() {
        return tipo;
    }

    abstract public boolean verify();

    abstract public List<Notifica> getNotifiche();
    
    abstract public ArrayList<Corso> getCorsi();
}