package com.ufl.model;

import java.time.LocalDate;

import com.ufl.dao.OnlineDAO;


public class Online extends Sessione {
    private String codice_meeting;

    public Online(int corso_id, LocalDate giorno_sessione, String codice_meeting) {
        super(corso_id, giorno_sessione);
        this.codice_meeting = codice_meeting;
    }

    @Override
     public Integer getIdSessione() {
        return OnlineDAO.getIdSessione(this);
    }

    public String getCodiceMeeting() {
        return codice_meeting;
    }


}
