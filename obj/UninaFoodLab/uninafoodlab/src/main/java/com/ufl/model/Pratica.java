package com.ufl.model;


import java.time.LocalDate;
import java.util.List;

public class Pratica extends Sessione {
    private String aula;
    private int posti_totali;

    public Pratica(Integer corso_id, LocalDate giorno_sessione, String aula, int posti_totali) {
        super(corso_id, giorno_sessione);
        this.aula = aula;
        this.posti_totali = posti_totali;
    }

    @Override
    public void recRicette(){
        // This method should retrieve the recipes for the practical session.
        // Implementation depends on how recipes are stored and associated with the session.
    }

    public String getAula() {
        return aula;
    }

    public int getPostiTotali() {
        return posti_totali;
    }

    public int getNStudentiIscritti() {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<Ingrediente> getIngredientiUsati(){
        // TODO Auto-generated method stub
        return null;
    }
}