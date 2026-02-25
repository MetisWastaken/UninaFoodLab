package com.ufl.model;
import java.util.List;
import com.ufl.dao.RicettaDAO;

public class Ricetta {
    private String nome;
    private String descrizione;
    private List<Ingrediente> ingredienti;
    

    public Ricetta(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void recIngredienti() {
        this.ingredienti = RicettaDAO.recIngredienti(this);
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public Integer getIdRicetta() {
        return RicettaDAO.getIdRicetta(this);
    }

}
