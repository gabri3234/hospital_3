package com.ejemplo.gestionhospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cama {

    private int id;
    private int habitacion_id;
    private int paciente_id;
    private String estado;

    // Constructor
    public Cama(int habitacion_id, int id, String estado) {
        this.habitacion_id = habitacion_id;
        this.id = id;
        this.estado = estado;
    }

    // Métodos

    // Obtener habitaciones con camas disponibles
    public static List<Integer> obtenerHabitacionesConCamasDisponibles(String url) {
        List<Integer> habitacionesDisponibles = new ArrayList<>();
        String query = "SELECT DISTINCT habitacion_id FROM camas WHERE estado = 'libre'";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                habitacionesDisponibles.add(rs.getInt("habitacion_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habitacionesDisponibles;
    }

    // Obtener camas disponibles para una habitación
    public static List<Integer> obtenerCamasDisponibles(String url, int habitacionId) {
        List<Integer> camasDisponibles = new ArrayList<>();
        String query = "SELECT id FROM camas WHERE habitacion_id = ? AND estado = 'libre'";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, habitacionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    camasDisponibles.add(rs.getInt("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return camasDisponibles;
    }

    // Asignar cama a un paciente
    public void asignarCama(String url, int pacienteId) {
        String query = "UPDATE camas SET estado = 'ocupada', paciente_id = ? WHERE habitacion_id = ? AND id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pacienteId);
            stmt.setInt(2, this.habitacion_id);
            stmt.setInt(3, this.id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
