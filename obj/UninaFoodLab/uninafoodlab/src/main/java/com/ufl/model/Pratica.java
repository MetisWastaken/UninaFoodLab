package com.ufl.model;


import java.time.LocalDate;
import java.util.List;

import com.ufl.dao.PraticaDAO;

public class Pratica extends Sessione {
    private String aula;
    private int posti_totali;

    public Pratica(Integer corso_id, LocalDate giorno_sessione, String aula, int posti_totali) {
        super(corso_id, giorno_sessione);
        this.aula = aula;
        this.posti_totali = posti_totali;
    }

    // ---- GET ----

    @Override
    public Integer getIdSessione() {
        return PraticaDAO.getIdSessione(this);
    }

    public String getAula() {
        return aula;
    }

    public int getPostiTotali() {
        return posti_totali;
    }

    public List<Ricetta> getRicette() {
        return PraticaDAO.getRicette(this);
    }

    public int getNStudentiIscritti() {
        return PraticaDAO.getNStudentiIscritti(this);
    }

    public String getStudentiIscritti() {
        return PraticaDAO.getStudentiIscritti(this);
    }

    public List<Ingrediente> getIngredientiPratica() {
        return PraticaDAO.getIngredientiPratica(this);
    }


    // ---- METODI ----

    public boolean insert() {
        return PraticaDAO.insert(this);
    }

    public boolean update(Pratica pratica) {
        return PraticaDAO.update(this.getIdSessione(), pratica);
    }

    public boolean delete() {
        return PraticaDAO.delete(this.getIdSessione());
    }

    public boolean aggiungiRicetta(Ricetta ricetta) {
        return PraticaDAO.aggiungiRicetta(this, ricetta);
    }
}