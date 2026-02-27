package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Date;

import java.time.LocalDate;

import com.ufl.model.Notifica;

public class NotificaDAO extends ConnessioneDAO {
    public static Notifica get(Integer id_Notifica){
        String query="SELECT * FROM notifica WHERE id_notifica=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, id_Notifica);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                String username_chef=rs.getString("username_chef");
                String titolo=rs.getString("titolo");
                Integer id_corso=rs.getInt("corso_id");

                return new Notifica(id_Notifica, username_chef, titolo, id_corso);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

    public static boolean insert(Notifica notifica){
        String query;
        if(notifica.getCorsoId() == null) {
            query = "INSERT INTO notifica (titolo, messaggio, solo_iscritti, data_creazione, username_chef)\r\n" + //
                    "    VALUES (?, ?, ?, ?, ?);";
        } else {
            query ="INSERT INTO notifica (titolo, messaggio, solo_iscritti, data_creazione, username_chef, corso_id)\r\n" + //
                        "    VALUES (?, ?, ?, ?, ?, ?);";   
        }
        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, notifica.getTitolo());
            ps.setString(2, notifica.getMessaggio());
            ps.setBoolean(3, notifica.isSoloIscritti());
            ps.setDate(4, Date.valueOf(notifica.getDataCreazione()));
            ps.setString(5, notifica.getUsernameChef());

            if(notifica.getCorsoId() != null) {
                ps.setInt(6, notifica.getCorsoId());
            }

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Integer id_notifica){
        String query = "CALL DeleteNotifica(?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_notifica);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String recMessaggio(Notifica notifica){
        String query = "SELECT messaggio FROM notifica WHERE id_notifica = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, notifica.getIdNotifica());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("messaggio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean recSoloIscritti(Notifica notifica){
        String query = "SELECT solo_iscritti FROM notifica WHERE id_notifica = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, notifica.getIdNotifica());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("solo_iscritti");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static LocalDate recDataCreazione(Notifica notifica){
        String query = "SELECT data_creazione FROM notifica WHERE id_notifica = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, notifica.getIdNotifica());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDate("data_creazione").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUsernameRiceventi(Integer id_notifica) {
        String query = "SELECT studente_username FROM view_notifiche_studente WHERE id_notifica = ?";
        StringBuilder usernames = new StringBuilder();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_notifica);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (usernames.length() > 0) {
                    usernames.append(" , ");
                }
                usernames.append(rs.getString("studente_username"));
            }
            
            return usernames.length() > 0 ? usernames.toString() : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
