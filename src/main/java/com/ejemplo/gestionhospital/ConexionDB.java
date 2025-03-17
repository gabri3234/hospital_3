package com.ejemplo.gestionhospital;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    public static Connection getConnection() {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost";
            String mydatabase = "hospital3";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "root";
            String password = "12345678";

            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // Si ocurre un error, se imprime el stack trace
            e.printStackTrace();
            return null;
        }
    }
}
