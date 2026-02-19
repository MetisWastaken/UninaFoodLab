package com.ufl.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.ufl.dao.StudenteDAO;

public class Studente extends Utente {
    private Map<Integer,LocalDate> date_iscrizione_corsi=null;

    public Studente(String username, String password) {
        super(username, password);
    }

    public Studente(String username, String password, String nome, String cognome) {
        super(username, password, nome, cognome, "Studente");
    }

    @Override
    public List<Notifica> getNotifiche() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Corso> getCorsi() {
        return StudenteDAO.getCorsi(this.username);
    }

    
    public boolean equals(Utente utente) {
        // This method should compare two Studente objects for equality.
        // Implementation depends on which attributes should be compared.
        return false; // Placeholder return statement
    }

    public void recDateIscrizioneCorsi(){
        // This method should retrieve the enrollment dates for courses.
        // Implementation depends on how enrollment dates are stored and associated with the student.
    }

    public Map<Integer, LocalDate> getDateIscrizioneCorsi() {
        return date_iscrizione_corsi;
    }

    public void iscrittoCorso(int id_corso) {
        date_iscrizione_corsi.put(id_corso, LocalDate.now());
    }

    public void disiscrittoCorso(int id_corso) {
        // This method should remove the student from a course.
        // Implementation depends on how course enrollments are managed.
    }

    public void iscrittoPratica(Integer id_corso, LocalDate giorno_sessione) {
        // TODO Auto-generated method stub
    }
    
    public void disiscrittoPratica(int id_corso, LocalDate giorno_sessione) {
        // This method should remove the student from a practical session.
        // Implementation depends on how practical session enrollments are managed.
    }
    
    //metodo da modificare o controllare in futuro
    public List<Pratica> getPratiche() {
        // TODO Auto-generated method stub
        return null;
    }

}