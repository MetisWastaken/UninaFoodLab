package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufl.model.Utente;

public class UtenteDAO extends ConnessioneDAO {
    

    public static Boolean verify(Utente utente){
        String query="SELECT * FROM utente WHERE username=? AND password=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, utente.getUsername());
            ps.setString(2, utente.getPassword());

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return false;
    }
}
