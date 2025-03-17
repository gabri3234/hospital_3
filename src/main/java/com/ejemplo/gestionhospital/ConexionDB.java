package com.ejemplo.gestionhospital;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    public static Connection getConnection() {
        try {
            // Cargar el driver de H2
            Class.forName("org.h2.Driver");

            // Establecer la conexión con la base de datos H2
            // La URL incluye la ruta relativa ./data/hospitaldb, donde se encuentra la base de datos
            // Usamos "sa" como nombre de usuario por defecto y "" para contraseña (ya que es "no auth")
            return DriverManager.getConnection("jdbc:h2:./data/hospitaldb");
        } catch (Exception e) {
            // Si ocurre un error, se imprime el stack trace
            e.printStackTrace();
            return null;
        }
    }
}
