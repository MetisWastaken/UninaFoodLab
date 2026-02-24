package com.ufl.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;

public class PraticaDAO extends ConnessioneDAO {
    public static Pratica get(Integer id_pratica){
        String query="SELECT * FROM pratica WHERE id_pratica=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, id_pratica);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                Integer corso_id=rs.getInt("corso_id");
                LocalDate giorno_sessione=rs.getDate("giorno_sessione").toLocalDate();
                String aula=rs.getString("aula");
                int posti_totali=rs.getInt("posti_totali");

                return new Pratica(corso_id, giorno_sessione, aula, posti_totali);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
    
    

    public static boolean insert(Pratica pratica) {
        String query = "CALL InsertPratica(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(pratica.getGiornoSessione()));
            ps.setString(2, pratica.getAula());
            ps.setInt(3, pratica.getPostiTotali());
            ps.setInt(4, pratica.getCorsoId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Integer id_pratica, Pratica pratica) {
        String query = "CALL UpdatePratica(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_pratica);
            ps.setDate(2, java.sql.Date.valueOf(pratica.getGiornoSessione()));
            ps.setString(3, pratica.getAula());
            ps.setInt(4, pratica.getPostiTotali());
            ps.setInt(5, pratica.getCorsoId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Integer id_pratica) {
        String query = "CALL DeletePratica(?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_pratica);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Integer getIdSessione(Pratica pratica) {
        String query = "SELECT id_pratica FROM pratica WHERE giorno_sessione = ? AND corso_id = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(pratica.getGiornoSessione()));
            ps.setInt(2, pratica.getCorsoId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("id_pratica");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Ricetta> recRicette(Pratica pratica){
        String query = "SELECT ps.ricetta_id FROM pratica_svolta ps WHERE ps.pratica_id = ?";
        ArrayList<Ricetta> ricette = new ArrayList<>();
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Integer id_ricetta = rs.getInt("ricetta_id");
                Ricetta ricetta = RicettaDAO.get(id_ricetta);
                if(ricetta != null){
                    ricette.add(ricetta);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ricette; 
    }

    public static String getStudentiIscritti(Pratica pratica){
        String query = "SELECT u.nome, u.cognome FROM iscritto_p ip JOIN utente u ON ip.stud_id = u.username WHERE ip.pratica_id = ?";
        StringBuilder studenti = new StringBuilder();
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                studenti.append(nome).append(" ").append(cognome).append("\n");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return studenti.toString();
    }

    public static boolean aggiungiRicetta(Pratica pratica, Ricetta ricetta){
        String query = "INSERT INTO pratica_svolta (pratica_id, ricetta_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            ps.setInt(2, ricetta.getIdRicetta());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
