package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.PreparedStatement;

import com.ufl.model.Online;

public class OnlineDAO extends ConnessioneDAO {
    public static Online get(String codice_meeting){
        String query="SELECT * FROM online WHERE codice_meeting=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, codice_meeting);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                Integer id_corso=rs.getInt("id_corso");
                LocalDate giorno_sessione=rs.getDate("data_ora").toLocalDate();

                return new Online(id_corso, giorno_sessione, codice_meeting);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

}