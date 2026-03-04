package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ufl.model.Ingrediente;
import com.ufl.model.Ricetta;

public class RicettaDAO extends ConnessioneDAO {

    // ---- GET ----

    public static Ricetta get(Integer id_ricetta) {
        String query = "SELECT * FROM ricetta WHERE id_ricetta = ?";
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

    public static Integer getIdRicetta(Ricetta ricetta) {
        String query = "SELECT id_ricetta FROM ricetta WHERE nome = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, ricetta.getNome());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_ricetta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---- REC ----

    public static ArrayList<Ingrediente> recIngredienti(Ricetta ricetta) {
        String query = "SELECT ingrediente_id FROM necessita WHERE ricetta_id = ?";
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        IngredienteDAO ingredienteDAO = new IngredienteDAO();
        Integer id_ricetta = ricetta.getIdRicetta();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, id_ricetta);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String nome = rs.getString("ingrediente_id");
                Ingrediente ing = ingredienteDAO.get(nome, id_ricetta);
                if (ing != null) {
                    ingredienti.add(ing);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredienti; 
    }
}