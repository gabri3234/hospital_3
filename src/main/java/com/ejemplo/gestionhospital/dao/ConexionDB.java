package com.ejemplo.gestionhospital.dao;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static String username;
    private static String password;

    public static Connection getConnection() {

        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            String url = "jdbc:mysql://localhost/hospital3";

            return DriverManager.getConnection(url, "root", "12345678");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setUsername(String username){
        ConexionDB.username = username;
    }

    public static void setPassword(String password){
        ConexionDB.password = password;
    }
}
