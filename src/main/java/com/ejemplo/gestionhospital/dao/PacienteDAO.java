package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {


    public void insertarPaciente(Paciente paciente) throws SQLException {
        String query = "INSERT INTO pacientes (nombre, apellido, dni, gravedad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
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

        }
    }

    public List<Paciente> obtenerPacientes() throws SQLException {
        String query = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
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
        }

        return pacientes;
    }

    public void eliminarPaciente(Paciente paciente) throws SQLException {
        String query = "UPDATE camas SET paciente_id = NULL WHERE paciente_id = " + paciente.getId();

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paciente.getId());
            stmt.executeUpdate();
        }

    }

    public List<Paciente> obtenerPacientesIngresados() throws SQLException {
        String query = "SELECT p.* FROM pacientes p JOIN camas c ON p.id = c.paciente_id WHERE c.paciente_id IS NOT NULL";

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
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
        }

        return pacientes;
    }

    public List<Paciente> obtenerPacientesNoIngresados() throws SQLException {
        String query = "SELECT p.* FROM pacientes p LEFT JOIN camas c ON p.id = c.paciente_id WHERE c.paciente_id IS NULL";

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
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
        }

        return pacientes;
    }
}
