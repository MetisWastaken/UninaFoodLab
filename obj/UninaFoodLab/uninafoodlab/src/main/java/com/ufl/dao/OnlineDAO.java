package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.Date;

import com.ufl.model.Online;



public class OnlineDAO extends ConnessioneDAO {

    // ---- GET ----

    public static Online get(Integer id_online){
        String query="SELECT * FROM online WHERE id_online=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, id_online);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                Integer corso_id=rs.getInt("corso_id");
                LocalDate giorno_sessione=rs.getDate("giorno_sessione").toLocalDate();
                String codice_meeting=rs.getString("codice_meeting");

                return new Online(corso_id, giorno_sessione, codice_meeting);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getIdSessione(Online online){
        String query = "SELECT id_online FROM online WHERE giorno_sessione = ? AND corso_id = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, Date.valueOf(online.getGiornoSessione()));
            ps.setInt(2, online.getCorsoId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("id_online");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- METODI ----

    public static boolean insert(Online online){
        String query = "CALL InsertOnline(?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, Date.valueOf(online.getGiornoSessione()));
            ps.setString(2, online.getCodiceMeeting());
            ps.setInt(3, online.getCorsoId());

            ps.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Integer id_online, Online online) {
        String query = "CALL UpdateOnline(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_online);
            ps.setDate(2, Date.valueOf(online.getGiornoSessione()));
            ps.setString(3, online.getCodiceMeeting());
            ps.setInt(4, online.getCorsoId());
            
            ps.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Integer id_online){
        String query = "CALL DeleteOnline(?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_online);
            
            ps.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}