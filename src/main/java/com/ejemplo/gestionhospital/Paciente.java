package com.ejemplo.gestionhospital;

import java.sql.*;

public class Paciente {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private int gravedad;

    public Paciente(String nombre, String apellido, String dni, int gravedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.gravedad = gravedad;
    }

    public void insertar() {
        String query = "INSERT INTO pacientes (nombre, apellido, dni, gravedad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, this.nombre);
            stmt.setString(2, this.apellido);
            stmt.setString(3, this.dni);
            stmt.setInt(4, this.gravedad);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}

