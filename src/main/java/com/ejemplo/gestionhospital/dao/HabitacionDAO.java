package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    private CamaDAO camaDAO = new CamaDAO();

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

    public List<Habitacion> obtenerHabitaciones() throws SQLException {
        String query = "SELECT * FROM habitaciones";
        List<Habitacion> habitaciones = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Habitacion habitacion = new Habitacion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("capacidad")
                );

                habitaciones.add(habitacion);
            }
        }

        return habitaciones;
    }

    public int obtenerEspacioDisponible(Habitacion habitacion) {

        int capacidadMax = habitacion.getCapacidad();

        try {
            int ocupacion = camaDAO.obtenerCamasHabitacionN(habitacion).size();
            return capacidadMax - ocupacion;
        } catch (SQLException e) {
            return -1;
        }

    }
}
