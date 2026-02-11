package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.ufl.model.Chef;
import com.ufl.model.Corso;
import com.ufl.model.Notifica;
import com.ufl.model.Studente;

public class ChefDAO extends ConnessioneDAO {

    public static Chef get(String username){

        String query="SELECT * FROM utente WHERE username=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                String tipo=rs.getString("tipo_utente");
                String password=rs.getString("password");
                String nome=rs.getString("nome");
                String cognome=rs.getString("cognome");
                
                if(tipo.equals("Chef")){
                    return new Chef(username, password, nome, cognome);
                }

            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

    public static List<Notifica> getNotifiche(Chef chef){
        String query="SELECT * FROM notifica WHERE username_chef=?";
        List<Notifica> notifiche = new ArrayList<>();

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, chef.getUsername());

            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                if(rs.getBoolean("solo_iscritti") == false){
                    notifiche.add(new Notifica(
                        rs.getString("username_chef"),
                        rs.getString("titolo"),
                        rs.getString("messaggio"),
                        rs.getDate("data_creazione").toLocalDate()
                    ));
                }
                else{
                    notifiche.add(new Notifica(
                        rs.getString("username_chef"),
                        rs.getString("titolo"),
                        rs.getString("messaggio"),
                        rs.getDate("data_creazione").toLocalDate(),
                        (Integer) rs.getObject("corso_id")
                    ));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return notifiche;
    }

    //public static  ArrayList<Corso> getCorsi()
    //public static void sggiungiReport(Report report)
    public static void aggiungiNotifica(Notifica notifica){
        String query="INSERT INTO notifica (username_chef, titolo, messaggio, data_creazione, solo_iscritti, corso_id) VALUES (?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, notifica.getUsernameChef());
            ps.setString(2, notifica.getTitolo());
            ps.setString(3, notifica.getMessaggio());
            ps.setDate(4, java.sql.Date.valueOf(notifica.getDataCreazione()));
            ps.setBoolean(5, notifica.isSoloIscritti());
            if(notifica.isSoloIscritti()){
                ps.setObject(6, notifica.getCorsoId(), java.sql.Types.INTEGER);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();            
        }
    }
}