package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.ufl.model.Chef;
import com.ufl.model.Corso;
import com.ufl.model.Notifica;
import com.ufl.model.Studente;

public class ChefDAO extends ConnessioneDAO {

    public static Chef get(String username){

        String query="SELECT * FROM utente WHERE username=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                String tipo=rs.getString("tipo_utente");
                String password=rs.getString("password");
                String nome=rs.getString("nome");
                String cognome=rs.getString("cognome");
                
                if(tipo.equals("Chef")){
                    return new Chef(username, password, nome, cognome);
                }

            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
}