package com.ufl.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Online extends Sessione {
    private String codice_meeting;

    public Online(int corso_id, LocalDate giorno_sessione, ArrayList<Ricetta> ricette, String codice_meeting) {
        super(corso_id, giorno_sessione, ricette);
        this.codice_meeting = codice_meeting;
    }
    
        public void recRicette(){
        // This method should retrieve the meeting code for the online session.
        // Implementation depends on how meeting codes are stored and associated with the session.
    }

    public String getCodiceMeeting() {
        return codice_meeting;
    }


}
