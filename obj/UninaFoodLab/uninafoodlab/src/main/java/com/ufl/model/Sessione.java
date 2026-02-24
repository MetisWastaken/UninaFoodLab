package com.ufl.model;

import java.time.LocalDate;

public abstract class Sessione {
    protected Integer corso_id;
    protected LocalDate giorno_sessione;

    public Sessione(Integer corso_id, LocalDate giorno_sessione) {
        this.corso_id = corso_id;
        this.giorno_sessione = giorno_sessione;
    }

    public Integer getCorsoId() {
        return corso_id;
    }

    public LocalDate getGiornoSessione() {
        return giorno_sessione;
    }

    abstract public Integer getIdSessione();

}