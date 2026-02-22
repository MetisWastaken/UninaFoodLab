package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import com.ufl.model.Online;
import com.ufl.model.Ricetta;


public class OnlineDAO extends ConnessioneDAO {

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

    public static ArrayList<Ricetta> recRicette(Online online){
        String query = "SELECT t.ricetta_id FROM teoria t WHERE t.online_id = ?";
        ArrayList<Ricetta> ricette = new ArrayList<>();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, online.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Integer id_ricetta = rs.getInt("ricetta_id");
                Ricetta ricetta = RicettaDAO.get(id_ricetta);
                if(ricetta != null) {
                    ricette.add(ricetta);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return ricette;
    }

    public static Integer getIdSessione(Online online) {
        String query = "SELECT id_online FROM online WHERE giorno_sessione = ? AND corso_id = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(online.getGiornoSessione()));
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
}