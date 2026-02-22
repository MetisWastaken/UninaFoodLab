package com.ufl.model;

import java.time.LocalDate;
import java.util.ArrayList;
public abstract class Sessione {
    protected Integer corso_id;
    protected LocalDate giorno_sessione;
    protected ArrayList<Ricetta> ricette= null;

    public Sessione(Integer corso_id, LocalDate giorno_sessione) {
        this.corso_id = corso_id;
        this.giorno_sessione = giorno_sessione;
    }

    public Integer getCorsoId() {
        return corso_id;
    }

    public LocalDate getGiornoSessione() {
        return giorno_sessione;
    }
    abstract public void recRicette();
    
    public ArrayList<Ricetta> getRicette() {
        return ricette;
    }

    abstract public Integer getIdSessione();

    public void aggiungiRicetta(Ricetta ricetta){
         // TODO Auto-generated method stub
    }

     public void cancellaRicetta(Ricetta ricetta){
         // TODO Auto-generated method stub
    }
}