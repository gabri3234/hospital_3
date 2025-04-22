package com.ejemplo.gestionhospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {

    public static List<Integer> obtenerCamasDisponibles(int habitacion_id) {
        List<Integer> camasDisponibles = new ArrayList<>();
        String query = "SELECT id FROM camas WHERE habitacion_id = ? AND estado = 'libre'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, habitacion_id);
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
}
