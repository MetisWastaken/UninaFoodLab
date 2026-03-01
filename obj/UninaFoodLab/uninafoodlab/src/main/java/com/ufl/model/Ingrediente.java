package com.ufl.model;

public class Ingrediente {
    private String nome;
    private String unit_misura;
    private double quantita;

    public Ingrediente(String nome, String unit_misura, double quantita) {
        this.nome = nome;
        this.unit_misura = unit_misura;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }   

    public String getUnitMisura() {
        return unit_misura;
    }

    public double getQuantita() {
        return quantita;
    }
}
