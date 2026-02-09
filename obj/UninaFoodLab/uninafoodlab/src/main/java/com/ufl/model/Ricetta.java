package com.ufl.model;
import java.util.List;

public class Ricetta {
    private String nome;
    private String descrizione;
    private List<Ingrediente> ingredienti;

    public Ricetta(String nome, String descrizione, List<Ingrediente> ingredienti) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.ingredienti = ingredienti;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void AggiungiIngrediente(Ingrediente ingrediente) {
        // TODO - da rivedere alla creazione della struttura DAO
    }

    public void Ingrediente(Ingrediente ingrediente) {
        // TODO - da rivedere alla creazione della struttura DAO
    }
}
