package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.PreparedStatement;

import com.ufl.model.Online;

public class OnlineDAO extends ConnessioneDAO {
    public static Online get(Integer corso_id, LocalDate giorno_sessione){
        String query="SELECT * FROM online WHERE corso_id=? AND giorno_sessione=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, corso_id);
            ps.setDate(2, java.sql.Date.valueOf(giorno_sessione));

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                String codice_meeting=rs.getString("codice_meeting");

                return new Online(corso_id, giorno_sessione, codice_meeting);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

}