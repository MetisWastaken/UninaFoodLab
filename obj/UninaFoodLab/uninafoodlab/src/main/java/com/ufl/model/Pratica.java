package com.ufl.model;


import java.time.LocalDate;
import java.util.List;

import com.ufl.dao.PraticaDAO;

public class Pratica extends Sessione {
    private String aula;
    private int posti_totali;
    private List<Ricetta> ricette = null;

    public Pratica(Integer corso_id, LocalDate giorno_sessione, String aula, int posti_totali) {
        super(corso_id, giorno_sessione);
        this.aula = aula;
        this.posti_totali = posti_totali;
    }

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

    public void recRicette(){
        ricette = PraticaDAO.recRicette(this);
    }

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public int getNStudentiIscritti(){
        return PraticaDAO.getNStudentiIscritti(this);
    }

    public String getStudentiIscritti(){
        return PraticaDAO.getStudentiIscritti(this);
    }

    public List<Ingrediente> getIngredientiPratica(){
        return PraticaDAO.getIngredientiPratica(this);
    }

    public boolean insert() {
        return PraticaDAO.insert(this);
    }

    public boolean update(Pratica pratica) {
        return PraticaDAO.update(this.getIdSessione(), pratica);
    }    
    
    public boolean delete() {
        return PraticaDAO.delete(this.getIdSessione());
    }

    public void aggiungiRicetta(Ricetta ricetta){
        if(PraticaDAO.aggiungiRicetta(this, ricetta)){
            ricette.add(ricetta);
        }
    }
}