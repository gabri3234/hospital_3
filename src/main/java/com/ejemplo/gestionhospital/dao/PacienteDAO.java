package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Paciente;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private final DataSource dataSource;

    public PacienteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertarPaciente(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombre, apellido, dni, gravedad) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getDni());
            stmt.setInt(4, paciente.getGravedad());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                paciente.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al insertar paciente.", e);
        }
    }

    public List<Paciente> obtenerPacientes() {
        String query = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getInt("gravedad")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener la lista de pacientes.", e);
        }

        return pacientes;
    }

    public void eliminarPaciente(Paciente paciente) {
        String query = "UPDATE camas SET paciente_id = NULL, estado = 'LIBRE' WHERE paciente_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, paciente.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AccessDataException("Error al eliminar paciente.", e);
        }
    }

    public List<Paciente> obtenerPacientesIngresados() {
        String query = "SELECT p.* FROM pacientes p JOIN camas c ON p.id = c.paciente_id WHERE c.paciente_id IS NOT NULL";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getInt("gravedad")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener pacientes ingresados.", e);
        }

        return pacientes;
    }

    public List<Paciente> obtenerPacientesNoIngresados() {
        String query = "SELECT p.* FROM pacientes p LEFT JOIN camas c ON p.id = c.paciente_id WHERE c.paciente_id IS NULL";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getInt("gravedad")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al obtener pacientes no ingresados.", e);
        }

        return pacientes;
    }
}
