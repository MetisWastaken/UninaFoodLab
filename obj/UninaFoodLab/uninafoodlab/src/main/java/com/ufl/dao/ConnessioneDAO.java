package com.ufl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDAO {
    private static String url = "jdbc:postgresql://localhost:5433/postgres";
    private static String username = "postgres";
    private static String password = "admin";

    static Connection connessione=getConnessione();

    public static Connection getConnessione(){
        try{
            Connection conn=DriverManager.getConnection(url,username,password);
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
