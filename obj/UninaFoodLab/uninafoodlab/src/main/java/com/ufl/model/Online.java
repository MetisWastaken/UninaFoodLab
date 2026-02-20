package com.ufl.model;

import java.time.LocalDate;

public class Online extends Sessione {
    private String codice_meeting;

    public Online(int corso_id, LocalDate giorno_sessione, String codice_meeting) {
        super(corso_id, giorno_sessione);
        this.codice_meeting = codice_meeting;
    }
    
    @Override
    public void recRicette(){
        // This method should retrieve the meeting code for the online session.
        // Implementation depends on how meeting codes are stored and associated with the session.
    }

    public String getCodiceMeeting() {
        return codice_meeting;
    }


}
