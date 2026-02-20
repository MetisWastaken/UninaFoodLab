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
                LocalDate giorno_sessione=rs.getDate("giorno_sessione").toLocalDate();
                String aula=rs.getString("aula");
                int posti_totali=rs.getInt("posti_totali");

                return new Pratica(id_pratica, giorno_sessione, aula, posti_totali);
            }
        }catch(SQLException e){
            e.printStackTrace();            
        }
        return null;
    }
}
