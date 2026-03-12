package com.ufl.model;
import java.util.Map;
import java.util.HashMap;
import com.ufl.dao.ReportDAO;

public class Report {
    private String username_chef;
    private int numero_corsi_totali;
    private int numero_sessioni_online;
    private int numero_sessioni_pratiche;
    private Map<Pratica, Integer> numero_ricette_per_pratica = new HashMap<>();
    private double media_ricette;
    private int max_ricette;
    private int min_ricette;


    public Report(String username_chef, int numero_corsi_totali, int numero_sessioni_online, int numero_sessioni_pratiche) {
        this.username_chef = username_chef;
        this.numero_corsi_totali = numero_corsi_totali;
        this.numero_sessioni_online = numero_sessioni_online;
        this.numero_sessioni_pratiche = numero_sessioni_pratiche;
        
    }

    // ---- GET ----

    public String getUsernameChef() {
        return username_chef;
    }

    public int getNumeroCorsiTotali() {
        return numero_corsi_totali;
    }

    public int getNumeroSessioniOnline() {
        return numero_sessioni_online;
    }

    public int getNumeroSessioniPratiche() {
        return numero_sessioni_pratiche;
    }

    public Map<Pratica, Integer> getNumeroRicettePerPratiche() {
        return numero_ricette_per_pratica;
    }

    public double getMediaRicette() {
        return media_ricette;
    }

    public int getMaxRicette() {
        return max_ricette;
    }

    public int getMinRicette() {
        return min_ricette;
    }


    // ---- REC ----

    public void recNumeroRicettePerPratiche() {
        numero_ricette_per_pratica = ReportDAO.getNumeroRicettePerPratiche(this.username_chef);
    }

    // ---- METODI ----
    public void calcolaStatisticheRicettePratiche() {
        if (numero_ricette_per_pratica == null || numero_ricette_per_pratica.isEmpty()) {
            media_ricette = 0.0;
            min_ricette = 0;
            max_ricette = 0;
            return;
        }
        
        int somma = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Integer n : numero_ricette_per_pratica.values()) {
            if (n == null) {
                continue;
            }
            somma = somma + n;
            if (n < min) min = n;
            if (n > max) max = n;
        }

        int count = numero_ricette_per_pratica.size();
        media_ricette = count == 0 ? 0.0 : (double) somma / count;
        min_ricette = (min == Integer.MAX_VALUE) ? 0 : min;
        max_ricette = (max == Integer.MIN_VALUE) ? 0 : max;
    }
}
