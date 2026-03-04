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

    // ---- GET ----

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public Integer getIdRicetta() {
        return RicettaDAO.getIdRicetta(this);
    }

    // ---- REC ----

    public void recIngredienti() {
        this.ingredienti = RicettaDAO.recIngredienti(this);
    }

}
