package com.ufl.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.ufl.model.Notifica;
import com.ufl.model.Studente;
import com.ufl.model.Corso;

public class StudenteDAO extends ConnessioneDAO{

    public static Studente get(String username){

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
                
                if(tipo.equals("Studente")){
                    return new Studente(username, password, nome, cognome);
                }

            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

    public static ArrayList<Corso> getCorsi(String username){
        
        String query="SELECT c.id, c.nome, c.categoria, c.data_in, c.data_fin, c.frequenza_settimanale, c.id_chef FROM corso c JOIN iscrizione_corsi ic ON c.id = ic.id_corso JOIN utente u ON ic.username = u.username WHERE u.username = ?";
        
        ArrayList<Corso> corsi = new ArrayList<>();
        
        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, username);
            
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                Integer id=rs.getInt("id");
                String nome=rs.getString("nome");
                String categoria=rs.getString("categoria");
                LocalDate data_in=rs.getDate("data_in").toLocalDate();
                LocalDate data_fin=rs.getDate("data_fin").toLocalDate();
                String frequenza_settimanale=rs.getString("frequenza_settimanale");

                Corso corso = new Corso(id, nome, categoria, data_in, data_fin, frequenza_settimanale, null, null, null);
                corsi.add(corso);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return corsi;
        //TODO:revisione necessaria posy revisione "grafici"
    }

    public static List<Notifica> getNotifiche(String username){
        
        String query="SELECT * FROM view_notifiche_studente WHERE studente_username=?";
        List<Notifica> notifiche = new ArrayList<>();
        
        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, username);
            
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                String username_studente=rs.getString("username_studente");
                String titolo=rs.getString("titolo");
                String messaggio=rs.getString("messaggio");
                boolean solo_iscritti=rs.getBoolean("solo_iscritti");
                int id_corso=rs.getInt("id_corso");

                //TODO: gestione if/else per notifiche solo iscritti o meno
                
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return notifiche;
    }
}

