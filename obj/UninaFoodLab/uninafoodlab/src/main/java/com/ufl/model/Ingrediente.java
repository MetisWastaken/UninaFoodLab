package com.ufl.model;

public class Ingrediente {
    private String nome;
    private String unita_misura;
    private double quantita;

    public Ingrediente(String nome, String unita_misura, double quantita) {
        this.nome = nome;
        this.unita_misura = unita_misura;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }   

    public String getUnita_misura() {
        return unita_misura;
    }

    public double getQuantita() {
        return quantita;
    }
}
