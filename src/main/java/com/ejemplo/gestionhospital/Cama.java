package com.ejemplo.gestionhospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cama {

    private int id;
    private int habitacion_id;
    private int paciente_id;
    private String estado;

    public Cama(int habitacion_id, int id, String estado) {
        this.habitacion_id = habitacion_id;
        this.id = id;
        this.estado = estado;
    }

    public static List<Integer> obtenerHabitacionesConCamasDisponibles() {
        List<Integer> habitacionesDisponibles = new ArrayList<>();
        String query = "SELECT DISTINCT habitacion_id FROM camas WHERE estado = 'libre'";

        try (Connection conn = ConexionDB.getConnection();
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

    public static List<Integer> obtenerCamasDisponibles(int habitacionId) {
        List<Integer> camasDisponibles = new ArrayList<>();
        String query = "SELECT id FROM camas WHERE habitacion_id = ? AND estado = 'libre'";

        try (Connection conn = ConexionDB.getConnection();
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

    public void asignarCama(int pacienteId) {
        String verificarQuery = "SELECT estado FROM camas WHERE habitacion_id = ? AND id = ?";
        String asignarQuery = "UPDATE camas SET estado = 'ocupada', paciente_id = ? WHERE habitacion_id = ? AND id = ? AND estado = 'libre'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarQuery);
             PreparedStatement asignarStmt = conn.prepareStatement(asignarQuery)) {

            verificarStmt.setInt(1, this.habitacion_id);
            verificarStmt.setInt(2, this.id);

            ResultSet rs = verificarStmt.executeQuery();
            if (rs.next() && rs.getString("estado").equals("libre")) {
                asignarStmt.setInt(1, pacienteId);
                asignarStmt.setInt(2, this.habitacion_id);
                asignarStmt.setInt(3, this.id);
                asignarStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
