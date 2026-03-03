package com.ufl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ufl.model.Report;
import com.ufl.model.Pratica;


public class ReportDAO extends ConnessioneDAO {
    public static Report get(String username_chef) throws SQLException {
        String query = "SELECT chef_username, numero_corsi_totali, numero_sessioni_online, numero_sessioni_pratiche FROM view_get_report WHERE chef_username = ?";
        
        try{
            PreparedStatement ps = connessione.prepareStatement(query);
            ps.setString(1, username_chef);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int numero_corsi_totali = rs.getInt("numero_corsi_totali");
                int numero_sessioni_online = rs.getInt("numero_sessioni_online");
                int numero_sessioni_pratiche = rs.getInt("numero_sessioni_pratiche");
                
                return new Report(username_chef, numero_corsi_totali, numero_sessioni_online, numero_sessioni_pratiche);
            }
        }catch(SQLException e){
            throw e;
        }
        return null;
    }

    public static Map<Pratica, Integer> getNumeroRicettePerPratiche(String username_chef) throws SQLException{
        String query = "SELECT id_pratica, numero_ricette_svolte FROM view_numero_ricette_per_sessione WHERE username_chef = ?";
        Map<Pratica, Integer> result = new HashMap<>();
        try{
            PreparedStatement ps= connessione.prepareStatement(query);
            ps.setString(1, username_chef);

            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Pratica pratica = PraticaDAO.get(rs.getInt("id_pratica"));
                result.put(pratica, rs.getInt("numero_ricette_svolte"));
            }
        }catch(SQLException e){
            throw e;
        }
        return result;
    }
}
