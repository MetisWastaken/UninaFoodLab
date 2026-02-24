package com.ufl.model;

import java.time.LocalDate;

public class Notifica {
    private Integer id_notifica = null;
    private String username_chef;
    private String titolo;
    private String messaggio;
    private boolean solo_iscritti=false;
    private LocalDate data_creazione;
    private Integer corso_id;

    public Notifica(Integer id_notifica, String username_chef, String titolo, Integer corso_id) {
        this.id_notifica = id_notifica;
        this.username_chef = username_chef;
        this.titolo = titolo;
        this.corso_id = corso_id;
    }

    public Notifica(String username_chef, String titolo, String messaggio, boolean solo_iscritti, LocalDate data_creazione, Integer corso_id) {
        this(null, username_chef, titolo, corso_id);
        this.messaggio = messaggio;
        this.solo_iscritti = solo_iscritti;
        this.data_creazione = data_creazione;
    }
    
    public void recIdNotifica() {
        // Implementazione per ricevere l'ID della notifica, se necessario
    }

    public Integer getIdNotifica() {
        return id_notifica;
    }

    public String getUsernameChef() {
        return username_chef;
    }

    public String getTitolo() {
        return titolo;
    }
    
    public void recMessagio(){
        // Implementazione per ricevere il messaggio completo della notifica, se necessario
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void recSoloIscritti() {
        // Implementazione per ricevere il valore booleano "solo_iscritti", se necessario
    }

    public boolean isSoloIscritti() {
        return solo_iscritti;
    }

    public void recDataCreazione(){
        // Implementazione per ricevere la data di creazione della notifica, se necessario
    }

    public LocalDate getDataCreazione() {
        return data_creazione;
    }

    public Integer getCorsoId() {
        return corso_id;
    }

    public String getUsernameRiceventi() {
        // Implementazione per ricevere gli username dei destinatari della notifica, se necessario
        return null;
    }
}   
