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

    @Override
    public boolean equals(Object obj){
        // This method should compare two Utente objects for equality.
        // Implementation depends on which attributes should be compared.
        return false; // Placeholder return statement
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void recNome(){
        // This method should retrieve the name of the user.
        // Implementation depends on how names are stored and associated with the user.
    }

    public String getNome() {
        return nome;
    }

    public void recCognome(){
        // This method should retrieve the surname of the user.
        // Implementation depends on how surnames are stored and associated with the user.
    }

    public String getCognome() {
        return cognome;
    }
    

    abstract public boolean verify();

    

    abstract public List<Notifica> getNotifiche();
    
    abstract public ArrayList<Corso> getCorsi();
}