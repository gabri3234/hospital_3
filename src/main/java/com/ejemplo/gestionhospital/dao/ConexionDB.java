package com.ejemplo.gestionhospital.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static DataSource dataSource;

    static {
        try (InputStream input = new FileInputStream("config/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");

            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(url);
            mysqlDS.setUser(username);
            mysqlDS.setPassword(password);

            dataSource = mysqlDS;

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración de la base de datos", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
