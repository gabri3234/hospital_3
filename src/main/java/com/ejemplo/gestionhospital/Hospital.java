package com.ejemplo.gestionhospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Hospital {

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

}
