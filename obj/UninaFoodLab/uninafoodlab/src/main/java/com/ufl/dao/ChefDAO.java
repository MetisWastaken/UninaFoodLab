package com.ufl.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import com.ufl.model.Chef;
import com.ufl.model.Notifica;
import com.ufl.model.Corso;
import com.ufl.model.Report;


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

    public static boolean verify(Chef chef){
        String query="SELECT * FROM utente WHERE username=? AND password=? AND tipo_utente='Chef'";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, chef.getUsername());
            ps.setString(2, chef.getPassword());

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                String tipo = rs.getString("tipo_utente");
                if(tipo.equals("Chef")){
                    return true; // metodo necessario per far visualizzare all'utente l'errore in futuro
                }
            }
            return false;

        }catch(SQLException e){
            e.printStackTrace();            
        }
        return false; 
    }

    public static List<Notifica> getNotifiche(Chef chef){
        String query = "SELECT id_notifica FROM notifica WHERE username_chef=?";
        List<Notifica> notifiche = new ArrayList<>();
        
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, chef.getUsername());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Integer id_notifica = rs.getInt("id_notifica");
                Notifica notifica = NotificaDAO.get(id_notifica);
                if(notifica != null){
                    notifiche.add(notifica);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return notifiche; 
    }

    public static ArrayList<Corso> getCorsi(Chef chef){
        String query = "SELECT id_corso FROM corso WHERE chef_id=?";
        ArrayList<Corso> corsi = new ArrayList<>();
        
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, chef.getUsername());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Integer id_corso = rs.getInt("id_corso");
                Corso corso = new CorsoDAO().get(id_corso);
                if(corso != null){
                    corsi.add(corso);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return corsi; 
    }

    public static Report recResoconto(Chef chef){
        return ReportDAO.get(chef.getUsername());
    }

    public static void aggiungiNotifica(Chef chef, Notifica notifica){
        // This method should add a notification for the given chef in the database.
        // Implementation depends on how notifications are stored and associated with the chef.
    }

    public static void eliminaNotifica(Chef chef, Integer id_notifica){
        // This method should remove a notification for the given chef from the database.
        // Implementation depends on how notifications are stored and associated with the chef.
    }

    public static void aggiungiCorso(Chef chef, Corso corso){
        // This method should add a course for the given chef in the database.
        // Implementation depends on how courses are stored and associated with the chef.
    }
}