package com.ejemplo.gestionhospital.dao;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionDB {

    private static String username;
    private static String password;
    private static String url;

    public static Connection getConnection() {

        try {

            InputStream input = new FileInputStream("config/config.properties");

            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");

            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);

            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
