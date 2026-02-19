package com.ufl.model;
import java.util.List;

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
        // This method should retrieve the list of ingredients for the recipe.
        // Implementation depends on how ingredients are stored and associated with the recipe.
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public Integer getIdRicetta() {
        // This method should retrieve the ID of the recipe.
        // Implementation depends on how recipe IDs are stored and associated with the recipe.
        return null;
    }

    public void AggiungiIngrediente(Ingrediente ingrediente) {
        // TODO - da rivedere alla creazione della struttura DAO
    }

    public void Ingrediente(Ingrediente ingrediente) {
        // TODO - da rivedere alla creazione della struttura DAO
    }
}
