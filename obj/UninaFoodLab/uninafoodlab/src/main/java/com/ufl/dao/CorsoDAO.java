package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.ufl.model.Corso;

import com.ufl.model.Chef;

public class CorsoDAO extends ConnessioneDAO {
    
    public Corso get(Integer id) {
        String query = "SELECT * FROM corso WHERE id_corso = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String descrizione = rs.getString("categoria");
                LocalDate dataIn = rs.getDate("data_in").toLocalDate();
                LocalDate dataFin = rs.getDate("data_fin").toLocalDate();
                String frequenzaSettimanale = rs.getString("frequenza_settimanale");

                return new Corso(id, nome, descrizione, dataIn, dataFin, frequenzaSettimanale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean insert(Corso corso){
        String query = "CALL InsertCorso(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, corso.getNome());
            ps.setString(2, corso.getCategoria());
            ps.setDate(3, java.sql.Date.valueOf(corso.getDataIn()));
            ps.setDate(4, java.sql.Date.valueOf(corso.getDataFin()));
            ps.setString(5, corso.getFrequenzaSettimanale());
            ps.setString(6, corso.getChef().getUsername());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
