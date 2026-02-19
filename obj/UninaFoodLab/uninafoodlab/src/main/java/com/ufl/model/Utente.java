package com.ufl.model;

import java.util.ArrayList;
import java.util.List;

import com.ufl.dao.UtenteDAO;

public abstract class Utente {
    protected String username;
    protected String password;
    protected String tipo;
    protected String nome;
    protected String cognome;
    

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Utente(String username, String password, String tipo) {
        this(username, password);
        this.tipo = tipo;
    }

    public Utente(String username, String password, String tipo, String nome, String cognome) {
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

    public void recTipo(){
        // This method should retrieve the type of the user.
        // Implementation depends on how user types are stored and associated with the user.
    }
    public void recNome(){
        // This method should retrieve the name of the user.
        // Implementation depends on how names are stored and associated with the user.
    }
    public void recCognome(){
        // This method should retrieve the surname of the user.
        // Implementation depends on how surnames are stored and associated with the user.
    }

    public String getTipo() {
        return tipo;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    

    public boolean verify(){
        return UtenteDAO.verify(this);
    }

    public boolean equals(Object obj){
        // This method should compare two Utente objects for equality.
        // Implementation depends on which attributes should be compared.
        return false; // Placeholder return statement
    }

    abstract public List<Notifica> getNotifiche();
    
    abstract public ArrayList<Corso> getCorsi();
}