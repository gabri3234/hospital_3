package com.ejemplo.gestionhospital;

import java.sql.*;

public class Cama {
    private int habitacionId;
    private int camaId;
    private String estado;

    public Cama(int habitacionId, int camaId, String estado) {
        this.habitacionId = habitacionId;
        this.camaId = camaId;
        this.estado = estado;
    }

    // Método para asignar una cama a un paciente
    public void asignarCama(String url, int pacienteId) {
        String query = "INSERT INTO camas (habitacion_id, paciente_id, estado) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url); // Conexión sin autenticación
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, habitacionId);
            stmt.setInt(2, pacienteId);
            stmt.setString(3, estado);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
