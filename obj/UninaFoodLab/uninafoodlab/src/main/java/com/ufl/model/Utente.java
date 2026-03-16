package com.ufl.model;

import com.ufl.dao.UtenteDAO;

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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Utente utente = (Utente) obj;
        return username.equals(utente.username);
    }
    
    // ---- GET ----

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

    // ---- REC ----

    public void recNome() {
        nome = UtenteDAO.recNome(this);
    }

    public void recCognome() {
        cognome = UtenteDAO.recCognome(this);
    }



}