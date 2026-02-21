package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufl.model.Ricetta;

public class RicettaDAO extends ConnessioneDAO {
    public static Ricetta get(Integer id_ricetta) {
      String  query= "SELECT * FROM ricetta WHERE id_ricetta = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_ricetta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                return new Ricetta(nome, descrizione);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}