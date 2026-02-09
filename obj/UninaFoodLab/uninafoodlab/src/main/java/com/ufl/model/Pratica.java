package com.ufl.model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Pratica extends Sessione {
    private String aula;
    private int posti_totali;

    public Pratica(Integer corso_id, LocalDate giorno_sessione, ArrayList<Ricetta> ricette, String aula, int posti_totali) {
        super(corso_id, giorno_sessione, ricette);
        this.aula = aula;
        this.posti_totali = posti_totali;
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