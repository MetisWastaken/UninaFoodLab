package com.ufl.model;

import java.time.LocalDate;
import java.util.List;

public class Corso {
    private Integer id=null;
    private String nome;
    private String categoria;
    private LocalDate data_in;
    private LocalDate data_fin;
    private String frequenza_settimanale;
    private Chef chef;
    private List<Pratica> sessioni_pratiche=null;
    private List<Online> sessioni_online=null;

    public Corso(String nome, String categoria, LocalDate data_in, LocalDate data_fin, String frequenza_settimanale, Chef chef) {
        this.nome = nome;
        this.categoria = categoria;
        this.data_in = data_in;
        this.data_fin = data_fin;
        this.frequenza_settimanale = frequenza_settimanale;
        this.chef = chef;
    }

    public void recId(){
        // This method should retrieve the ID of the course, if necessary.
        // Implementation depends on how the ID is generated and stored in the database.
    }

    public Integer getId() {
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
    
    public void recSessioniPratiche(){
        // This method should retrieve the list of practical sessions for the course.
        // Implementation depends on how sessions are stored and associated with the course.
    }

    public List<Pratica> getSessioniPratiche() {
        return sessioni_pratiche;
    }

    public void recSessioniOnline(){
        // This method should retrieve the list of online sessions for the course.
        // Implementation depends on how sessions are stored and associated with the course.
    }

    public List<Online> getSessioniOnline() {
        return sessioni_online;
    }

    public String getStudentiIscritti(){
        // This method should retrieve the list of enrolled students for the course.
        // Implementation depends on how student enrollments are stored and associated with the course.
        return null;
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

    public void eliminaSessione(Pratica sessione, Notifica notifica) {
        // This method should remove an existing practical session.
        // Implementation depends on how sessions are identified and stored.
    }

    public void eliminaSessione(Online sessione, Notifica notifica) {
        // This method should remove an existing online session.
        // Implementation depends on how sessions are identified and stored.
    }
}
