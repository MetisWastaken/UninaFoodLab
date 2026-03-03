package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ufl.model.Corso;
import com.ufl.model.Online;
import com.ufl.model.Pratica;
import com.ufl.model.Chef;

public class CorsoDAO extends ConnessioneDAO {
    
    public static Corso get(Integer id) throws SQLException {
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
            throw e;
        }
        return null;
    }

    public static boolean insert(Corso corso) throws SQLException {
        String query = "CALL InsertCorso(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, corso.getNome());
            ps.setString(2, corso.getCategoria());
            ps.setDate(3, Date.valueOf(corso.getDataIn()));
            ps.setDate(4, Date.valueOf(corso.getDataFin()));
            ps.setString(5, corso.getFrequenzaSettimanale());
            ps.setString(6, corso.getChef().getUsername());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Chef recChef(Corso corso) throws SQLException {
        String query = "SELECT chef_id FROM corso WHERE id_corso = ?";
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, corso.getId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String chefUsername = rs.getString("chef_id");
                return ChefDAO.get(chefUsername);
            }
        } catch(SQLException e) {
            throw e;
        }
        return null;
    }

    public static List<Pratica> recSessioniPratiche(Corso corso) throws SQLException {
        String query = "SELECT id_pratica FROM pratica WHERE corso_id = ?";
        List<Pratica> pratiche = new ArrayList<>();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, corso.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Integer id_pratica = rs.getInt("id_pratica");
                Pratica pratica = PraticaDAO.get(id_pratica);
                if(pratica != null) {
                    pratiche.add(pratica);
                }
            }
        } catch(SQLException e) {
            throw e;
        }
        return pratiche;
    }

    public static List<Online> recSessioniOnline(Corso corso) throws SQLException {
        String query = "SELECT id_online FROM online WHERE corso_id = ?";
        List<Online> pratiche = new ArrayList<>();
        try {
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, corso.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Integer id_online = rs.getInt("id_online");
                Online online = OnlineDAO.get(id_online);
                if(online != null) {
                    pratiche.add(online);
                }
            }
        } catch(SQLException e) {
            throw e;
        }
        return pratiche;
    }

    public static String getStudentiIscritti(Corso corso) throws SQLException {
        String query = "SELECT u.nome, u.cognome FROM iscritto_c ic JOIN utente u ON ic.stud_id = u.username WHERE ic.corso_id = ?";
        StringBuilder studenti = new StringBuilder();
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setInt(1, corso.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                studenti.append(nome).append(" ").append(cognome);
                if(rs.isLast()) {
                    studenti.append(".");
                } else {
                    studenti.append(", ");
                }
            }
        }catch(SQLException e){
            throw e;
        }
        return studenti.toString();
    }
}
