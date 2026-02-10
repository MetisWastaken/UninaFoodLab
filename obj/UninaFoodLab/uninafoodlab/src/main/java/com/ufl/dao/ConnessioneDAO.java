package com.ufl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDAO {
    private static String url = "jdbc:postgresql://localhost:5432/uninafoodlab";
    private static String username = "postgres";
    private static String password = "root";

    static Connection connessione=getConnessione();

    public static Connection getConnessione(){
        try{
            Connection connessione=DriverManager.getConnection(url,username,password);
            return connessione;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}


