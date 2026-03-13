package com.ufl.model;

import java.time.LocalDate;

import com.ufl.dao.NotificaDAO;

public class Notifica {
    private Integer id_notifica = null;
    private String username_chef;
    private String titolo;
    private String messaggio;
    private boolean solo_iscritti=false;
    private LocalDate data_creazione=null;
    private Integer corso_id;

    public Notifica(Integer id_notifica, String username_chef, String titolo, Integer corso_id) {
        this.id_notifica = id_notifica;
        this.username_chef = username_chef;
        this.titolo = titolo;
        this.corso_id = corso_id;
    }

    public Notifica(String username_chef, String titolo, String messaggio, boolean solo_iscritti, Integer corso_id) {
        this(null, username_chef, titolo, corso_id);
        this.messaggio = messaggio;
        this.solo_iscritti = solo_iscritti;
    }

    // ---- GET ----

    public Integer getIdNotifica() {
        return id_notifica;
    }

    public String getUsernameChef() {
        return username_chef;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public boolean isSoloIscritti() {
        return solo_iscritti;
    }

    public LocalDate getDataCreazione() {
        return data_creazione;
    }

    public Integer getCorsoId() {
        return corso_id;
    }

    public String getUsernameRiceventi() {
        return NotificaDAO.getUsernameRiceventi(this.id_notifica);
    }

    // ---- REC ----

    public void recMessagio() {
        messaggio = NotificaDAO.recMessaggio(this);
    }

    public void recSoloIscritti() {
        solo_iscritti = NotificaDAO.recSoloIscritti(this);
    }

    public void recDataCreazione() {
        data_creazione = NotificaDAO.recDataCreazione(this);
    }

    // ---- METODI ----

    public boolean insert() {
        return NotificaDAO.insert(this);
    }

    public boolean delete() {
        return NotificaDAO.delete(this.id_notifica);
    }
}   
