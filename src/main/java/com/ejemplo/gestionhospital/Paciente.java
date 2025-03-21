package com.ejemplo.gestionhospital;

import java.sql.*;

public class Paciente {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private int gravedad;

    // Constructor
    public Paciente(String nombre, String apellido, String dni, int gravedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.gravedad = gravedad;
    }

    // Obtener lista de pacientes
    public static void obtenerListaPacientes() {
        String query = "SELECT p.id, p.nombre, p.apellido, p.dni, p.gravedad, " +
                "COALESCE(c.habitacion_id, 0) AS habitacion_id, COALESCE(c.id, 0) AS cama_id " +
                "FROM pacientes p LEFT JOIN camas c ON p.id = c.paciente_id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nombre: " + rs.getString("nombre") +
                        ", Apellido: " + rs.getString("apellido") +
                        ", DNI: " + rs.getString("dni") +
                        ", Estado de gravedad: " + rs.getInt("gravedad") +
                        ", Habitación: " + (rs.getInt("habitacion_id") == 0 ? "N/A" : rs.getInt("habitacion_id")) +
                        ", Cama: " + (rs.getInt("cama_id") == 0 ? "N/A" : rs.getInt("cama_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insertar paciente en la base de datos
    public void insertarPaciente() {
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

