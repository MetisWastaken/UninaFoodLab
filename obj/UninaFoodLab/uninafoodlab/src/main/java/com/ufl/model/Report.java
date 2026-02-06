package com.ufl.model;
import java.util.Map;

public class Report {

    private int numero_corsi_totali;
    private int numero_sessioni_online;
    private int numero_sessioni_pratiche;
    private Map<Sessione, Integer> numero_ricette_per_sessione;

    public Report(int numero_corsi_totali, int numero_sessioni_online, int numero_sessioni_pratiche, Map<Sessione, Integer> numero_ricette_per_sessione) {
        this.numero_corsi_totali = numero_corsi_totali;
        this.numero_sessioni_online = numero_sessioni_online;
        this.numero_sessioni_pratiche = numero_sessioni_pratiche;
        this.numero_ricette_per_sessione = numero_ricette_per_sessione;
    }

    public void generaStatistiche() {
        // TODO: il controller calcolerà i valori e li passerà al model
    }

    public void visualizzaGrafico() {
        // TODO: compito della view
    }

    // getters 

    public int getNumeroCorsiTotali() {
        return numero_corsi_totali;
    }

    public int getNumeroSessioniOnline() {
        return numero_sessioni_online;
    }

    public int getNumeroSessioniPratiche() {
        return numero_sessioni_pratiche;
    }

    public Map<Sessione, Integer> getNumeroRicettePerSessione() {
        return new Map<>(numero_ricette_per_sessione);
    }
}
