package com.ufl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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
                String titolo=rs.getString("testo");
                Integer id_corso=rs.getInt("id_corso");

                return new Notifica(id_Notifica, username_chef, titolo, id_corso);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }

}
