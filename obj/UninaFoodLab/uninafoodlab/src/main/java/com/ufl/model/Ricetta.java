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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ricetta ricetta = (Ricetta) obj;
        return nome.equals(ricetta.nome);
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
