package com.ufl.dao;

import static com.ufl.dao.ConnessioneDAO.password;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.ufl.model.Notifica;
import com.ufl.model.Studente;
import com.ufl.model.Corso;

public class StudenteDAO extends ConnessioneDAO{

    public static Studente get(String username){

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
                
                if(tipo.equals("Studente")){
                    return new Studente(username, password, nome, cognome);
                }

            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
}