package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamaDAO {

    private final DataSource dataSource;

    public CamaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertarCama(Cama cama) {
        String query = "INSERT INTO camas (habitacion_id, paciente_id, estado) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cama.getHabitacion_id());
            stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setString(3, cama.getEstado());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cama.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al insertar una cama", e);
        }
    }

    public void actualizarEstadoCama(Cama cama) {
        String query = "UPDATE camas SET estado = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cama.getEstado());
            stmt.setInt(2, cama.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AccessDataException("Error al actualizar el estado de la cama", e);
        }
    }

    public List<Cama> obtenerCamas() {
        String query = "SELECT * FROM camas";
        List<Cama> camas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
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

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener la lista de camas", e);
        }

        return camas;
    }

    public List<Cama> obtenerCamasHabitacionN(Habitacion habitacion) {
        String query = "SELECT * FROM camas WHERE habitacion_id = ?";
        List<Cama> camas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener camas de la habitación", e);
        }

        return camas;
    }

    public List<Cama> obtenerCamasLibresHabitacionN(Habitacion habitacion) {
        String query = "SELECT * FROM camas WHERE habitacion_id = ? AND paciente_id IS NULL";
        List<Cama> camas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener camas de la habitación", e);
        }

        return camas;
    }

    public List<Cama> obtenerCamasLibres() {
        String query = "SELECT * FROM camas WHERE paciente_id IS NULL";
        List<Cama> camas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener camas libres", e);
        }

        return camas;
    }

    public void asignarCamaPaciente(int pacienteId, int camaId) {
        String query = "UPDATE camas SET paciente_id = ?, estado = 'OCUPADA' WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pacienteId);
            stmt.setInt(2, camaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AccessDataException("Error al asignar cama al paciente", e);
        }
    }
}
