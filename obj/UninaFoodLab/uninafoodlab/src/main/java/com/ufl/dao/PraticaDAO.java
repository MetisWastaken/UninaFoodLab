package com.ufl.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.ufl.dao.RicettaDAO;
import com.ufl.dao.IngredienteDAO;

import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;
import com.ufl.model.Ingrediente;

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
    
    public static ArrayList<Ricetta> recRicette(Pratica pratica){
        String query = "SELECT ps.ricetta_id FROM pratica_svolta ps WHERE ps.pratica_id = ?";
        ArrayList<Ricetta> ricette = new ArrayList<>();
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Integer id_ricetta = rs.getInt("ricetta_id");
                Ricetta ricetta = RicettaDAO.get(id_ricetta);
                if(ricetta != null){
                    ricette.add(ricetta);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ricette; 
    }

    public static Integer getIdSessione(Pratica pratica) {
        String query = "SELECT id_pratica FROM pratica WHERE giorno_sessione = ? AND corso_id = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(pratica.getGiornoSessione()));
            ps.setInt(2, pratica.getCorsoId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("id_pratica");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getNStudentiIscritti(Pratica pratica) {
        String query = "SELECT posti_occupati FROM view_studenti_iscritti WHERE id_pratica = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("posti_occupati");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Ingrediente> getIngredientiUsati(Pratica pratica) {
        String query = "SELECT ingrediente_nome, unit_misura, quantita FROM view_ingredienti_pratica WHERE id_pratica = ?";
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, pratica.getIdSessione());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                String nome = rs.getString("ingrediente_nome");
                String unita_misura = rs.getString("unit_misura");
                double quantita = rs.getDouble("quantita");
                
                ingredienti.add(new Ingrediente(nome, unita_misura, quantita));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return ingredienti;
    }
}
