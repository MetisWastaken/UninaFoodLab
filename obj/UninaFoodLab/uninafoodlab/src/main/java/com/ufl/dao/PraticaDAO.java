package com.ufl.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import com.ufl.model.Pratica;

public class PraticaDAO extends ConnessioneDAO {
    public static Pratica get(Integer id_pratica){
        String query="SELECT * FROM pratica WHERE id_pratica=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, id_pratica);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                Integer corso_id=rs.getInt("corso_id");
                LocalDate giorno_sessione=rs.getDate("giorno_sessione").toLocalDate();
                String aula=rs.getString("aula");
                int posti_totali=rs.getInt("posti_totali");

                return new Pratica(corso_id, giorno_sessione, aula, posti_totali);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
       public static Pratica get(Integer corso_id, LocalDate giorno_sessione){
        String query="SELECT * FROM pratica WHERE corso_id=? AND giorno_sessione=?";

        try{
            PreparedStatement ps=connessione.prepareStatement(query);
            ps.setInt(1, corso_id);
            ps.setDate(2, java.sql.Date.valueOf(giorno_sessione));

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                String aula=rs.getString("aula");
                int posti_totali=rs.getInt("posti_totali");

                return new Pratica(corso_id, giorno_sessione, aula, posti_totali);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
}
