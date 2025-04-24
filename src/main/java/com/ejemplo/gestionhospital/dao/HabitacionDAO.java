package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.model.Habitacion;
import com.ejemplo.gestionhospital.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    public void insertarHabitacion(Habitacion habitacion) throws SQLException {
        String query = "INSERT INTO habitaciones (nombre, capacidad) VALUES (?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, habitacion.getNombre());
            stmt.setInt(2, habitacion.getCapacidad());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                habitacion.setId(rs.getInt(1));
            }

        }
    }

    public static List<Integer> obtenerHabitacionesDisponibles() {
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
