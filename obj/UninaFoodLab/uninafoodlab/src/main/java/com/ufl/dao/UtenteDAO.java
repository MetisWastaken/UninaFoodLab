package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufl.model.Utente;

public class UtenteDAO extends ConnessioneDAO {

    public static   String recCognome(Utente utente){
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
    
        public static String recNome(Utente utente){

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

        public static String inizializzaUtente(Boolean isChef, Boolean isStudente, Utente utente){
            String sql = "SELECT tipo_utente FROM utente WHERE username = ?";
            
            try {
                PreparedStatement ps = connessione.prepareStatement(sql);
                ps.setString(1, utente.getUsername());
                
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    String tipo_utente = rs.getString("tipo_utente");
                    
                    if (isChef && !isStudente && tipo_utente.equals("Chef")) {
                        return "Chef";
                    } else if (isStudente && !isChef && tipo_utente.equals("Studente")) {
                        return "Studente";
                    }
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
   
