package com.ufl.model;

import java.time.LocalDate;
import java.util.List;

public class Corso {
    private int id;
    private String nome;
    private String categoria;
    private LocalDate data_in;
    private LocalDate data_fin;
    private String frequenza_settimanale;
    private Chef chef;
    private List<Pratica> sessioni_pratiche;
    private List<Online> sessioni_online;

    public Corso(int id, String nome, String categoria, LocalDate data_in, LocalDate data_fin, String frequenza_settimanale, Chef chef, List<Pratica> sessioni_pratiche, List<Online> sessioni_online) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.data_in = data_in;
        this.data_fin = data_fin;
        this.frequenza_settimanale = frequenza_settimanale;
        this.chef = chef;
        this.sessioni_pratiche = sessioni_pratiche;
        this.sessioni_online = sessioni_online;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getDataIn() {
        return data_in;
    }

    public LocalDate getDataFin() {
        return data_fin;
    }

    public String getFrequenzaSettimanale() {
        return frequenza_settimanale;
    }

    public Chef getChef() {
        return chef;
    }

    public List<Pratica> getSessioniPratiche() {
        return sessioni_pratiche;
    }

    public List<Online> getSessioniOnline() {
        return sessioni_online;
    }

    public List<Studente> getStudenti() {
        // This method should return the list of students enrolled in the course.
        // Implementation depends on how students are associated with the course.
        return null; // Placeholder return statement
    }

    public void aggiungiSessione(Pratica sessione) {
        sessioni_pratiche.add(sessione);
    }

    public void aggiungiSessione(Online sessione) {
        sessioni_online.add(sessione);
    }

    public void modificaSessione(Pratica sessione, Notifica notifica) {
        // This method should modify an existing practical session.
        // Implementation depends on how sessions are identified and stored.
    }

    public void modificaSessione(Online sessione, Notifica notifica) {
        // This method should modify an existing online session.
        // Implementation depends on how sessions are identified and stored.
    }
}
