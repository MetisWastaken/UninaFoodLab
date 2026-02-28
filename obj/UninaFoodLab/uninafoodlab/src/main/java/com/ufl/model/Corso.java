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
    private List<Pratica> sessioni_pratiche=null;
    private List<Online> sessioni_online=null;

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
    
    public void recChef(){
        chef = CorsoDAO.recChef(this);
    } 
    
    public Chef getChef() {
        return chef;
    }
    
    public void recSessioniPratiche(){
        sessioni_pratiche = CorsoDAO.recSessioniPratiche(this);
    }

    public List<Pratica> getSessioniPratiche() {
        return sessioni_pratiche;
    }

    public void recSessioniOnline(){
        sessioni_online = CorsoDAO.recSessioniOnline(this);
    }

    public List<Online> getSessioniOnline() {
        return sessioni_online;
    }

    public String getStudentiIscritti(){
        return CorsoDAO.getStudentiIscritti(this);
    }

    public void insert(){
        CorsoDAO.insert(this);
    }

    public void aggiungiSessione(Pratica sessione) {
        if(sessione.insert()){
            sessioni_pratiche.add(sessione);
        }
        
    }

    public void aggiungiSessione(Online sessione) {
        if(sessione.insert()){
            sessioni_online.add(sessione);
        }
    }

    public void modificaSessione(Pratica sessione1,Pratica sessione2, Notifica notifica) {
        if(sessione1.update(sessione2)){
            notifica.insert();
            sessioni_pratiche.replaceAll(s -> s.equals(sessione1) ? sessione2 : s);
        }
    }

    public void modificaSessione(Online sessione1,Online sessione2, Notifica notifica) {
        if(sessione1.update(sessione2)){
            notifica.insert();
            sessioni_online.replaceAll(s -> s.equals(sessione1) ? sessione2 : s);
        }
    }

    public void eliminaSessione(Pratica sessione, Notifica notifica) {
        if(sessione.delete()){
            notifica.insert();
           sessioni_pratiche.remove(sessione);
        }
    }

    public void eliminaSessione(Online sessione, Notifica notifica) {
        if(sessione.delete()){
            notifica.insert();
            sessioni_online.remove(sessione);
        }
    }
}
