package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufl.model.Utente;

public class UtenteDAO extends ConnessioneDAO {
    
    public static String recNome(Utente utente) {

        String sql = "SELECT nome FROM utente WHERE username = ?";
        
        try 
        {
            PreparedStatement ps = connessione.prepareStatement(sql);
            ps.setString(1, utente.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("nome");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();

        }
        
        return null;
    }

    public static String recCognome(Utente utente) {
        String sql = "SELECT cognome FROM utente WHERE username = ?";
        
        try 
        {
            PreparedStatement ps = connessione.prepareStatement(sql);
            ps.setString(1, utente.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("cognome");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();

        }
        
        return null;
    }
}
   
