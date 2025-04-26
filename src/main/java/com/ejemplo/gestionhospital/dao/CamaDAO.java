package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamaDAO {

    public void insertarCama(Cama cama) throws SQLException {
        String query = "INSERT INTO camas (habitacion_id, paciente_id, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cama.getHabitacion_id());
            stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setString(3, cama.getEstado());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cama.setId(rs.getInt(1));
            }
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

    public List<Cama> obtenerCamas() throws SQLException {
        String query = "SELECT * FROM camas";
        List<Cama> camas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Cama cama = new Cama(
                        rs.getInt("id"),
                        rs.getInt("habitacion_id"),
                        rs.getString("estado"),
                        rs.getInt("paciente_id")
                );

                camas.add(cama);
            }
        }

        return camas;
    }

    public List<Cama> obtenerCamasHabitacionN(Habitacion habitacion) throws SQLException {

        String query = "SELECT * FROM camas WHERE habitacion_id = ?";
        List<Cama> camas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, habitacion.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cama cama = new Cama(
                        rs.getInt("id"),
                        rs.getInt("habitacion_id"),
                        rs.getString("estado"),
                        rs.getInt("paciente_id")
                );

                camas.add(cama);
            }
        }

        return camas;
    }

    public List<Cama> obtenerCamasLibres() throws SQLException {

        String query = "SELECT * FROM camas WHERE paciente_id IS NULL";
        List<Cama> camas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cama cama = new Cama(
                        rs.getInt("id"),
                        rs.getInt("habitacion_id"),
                        rs.getString("estado"),
                        rs.getInt("paciente_id")
                );

                camas.add(cama);
            }
        }

        return camas;
    }

    public void asignarCamaPaciente(int pacienteId, int camaId) throws SQLException {
        String query = "UPDATE camas SET paciente_id = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pacienteId);
            stmt.setInt(2, camaId);
            stmt.executeUpdate();

        }

    }
}


