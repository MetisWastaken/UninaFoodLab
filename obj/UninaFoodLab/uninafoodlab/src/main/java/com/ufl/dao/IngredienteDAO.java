package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.ufl.model.Ingrediente;

public class IngredienteDAO extends ConnessioneDAO  {
    public Ingrediente get(String nome, Integer id_ricetta){
        String query = "SELECT i.unit_misura, n.quant_ing FROM ingrediente i JOIN necessita n ON i.nome = n.ingrediente_id WHERE i.nome = ? AND n.ricetta_id = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, nome);
            ps.setInt(2, id_ricetta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String unita_misura = rs.getString("unit_misura");
                double quantita = rs.getDouble("quant_ing");

                return new Ingrediente(nome, unita_misura, quantita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
