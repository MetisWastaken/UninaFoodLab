package com.ufl.model;

import java.time.LocalDate;
import java.util.List;

import com.ufl.dao.CorsoDAO;

public class Corso {
    private Integer id=null;
    private String nome;
    private String categoria;
    private LocalDate data_in;
    private LocalDate data_fin;
    private String frequenza_settimanale;
    private Chef chef = null;

    public Corso(Integer id, String nome, String categoria, LocalDate data_in, LocalDate data_fin, String frequenza_settimanale) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.data_in = data_in;
        this.data_fin = data_fin;
        this.frequenza_settimanale = frequenza_settimanale;
    }

    public Corso(String nome, String categoria, LocalDate data_in, LocalDate data_fin, String frequenza_settimanale, Chef chef) {
        this.nome = nome;
        this.categoria = categoria;
        this.data_in = data_in;
        this.data_fin = data_fin;
        this.frequenza_settimanale = frequenza_settimanale;
        this.chef = chef;
    }

    // ---- GET ----

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

    public List<Pratica> getSessioniPratiche() {
        return CorsoDAO.getSessioniPratiche(this);
    }

    public List<Online> getSessioniOnline() {
        return CorsoDAO.getSessioniOnline(this);
    }

    public String getStudentiIscritti() {
        return CorsoDAO.getStudentiIscritti(this);
    }

    // ---- REC ----

    public void recChef() {
        chef = CorsoDAO.recChef(this);
    }

    // ---- METODI ----

    public boolean insert() {
        return CorsoDAO.insert(this);
    }
}
