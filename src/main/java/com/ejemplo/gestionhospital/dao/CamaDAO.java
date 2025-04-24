package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Paciente;

import java.sql.*;

public class CamaDAO {

    public void insertarCama(Cama cama) throws SQLException {
        String query = "INSERT INTO camas (habitacion_id, paciente_id, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cama.getHabitacion_id());
            stmt.setInt(2, cama.getPaciente_id());
            stmt.setString(3, cama.getEstado());
            stmt.executeUpdate();

        }
    }

    public void actualizarEstadoCama(Cama cama) throws SQLException {
        String query = "UPDATE camas SET estado = ? WHERE id = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cama.getEstado());
            stmt.setInt(2, cama.getId());
            stmt.executeUpdate();
        }
    }

}
