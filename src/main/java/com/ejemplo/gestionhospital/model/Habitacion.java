package com.ejemplo.gestionhospital.model;

import com.ejemplo.gestionhospital.dao.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {

    private int id;
    private String nombre;
    private int capacidad;

    public Habitacion(String nombre, int capacidad){
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Habitacion(int id, String nombre, int capacidad){
        this(nombre, capacidad);
        this.id = id;
    }



    // SETTERS AND GETTERS --------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

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
