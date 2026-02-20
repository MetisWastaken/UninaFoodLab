package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufl.model.Ricetta;
public class RicettaDAO extends ConnessioneDAO {
    public Ricetta get(String nome) {
      String  query= "SELECT * FROM ricetta WHERE nome = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, nome);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String descrizione = rs.getString("descrizione");
                return new Ricetta(nome, descrizione);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}