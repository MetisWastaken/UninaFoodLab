package com.ufl;
import com.ufl.dao.ConnessioneDAO;
import java.sql.Connection;



public class Main {
    public static void main(String[] args) {
        Connection connessione = ConnessioneDAO.getConnessione();
        if (connessione != null) {
            System.out.println("Connessione al database avvenuta con successo!");
        } else {
            System.out.println("Errore nella connessione al database.");
        }
    }
}
