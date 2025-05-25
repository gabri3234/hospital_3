package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.HabitacionDAO;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Habitacion;

import java.util.List;

public class RoomService {

    private final HabitacionDAO habitacionDAO;

    public RoomService() {
        habitacionDAO = new HabitacionDAO();
    }

    public List<Habitacion> obtenerHabitaciones() {
        try {
            return habitacionDAO.obtenerHabitaciones();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener habitaciones: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public int obtenerEspacioDisponible(Habitacion habitacion) {
        try {
            return habitacionDAO.obtenerEspacioDisponible(habitacion);
        } catch (AccessDataException e) {
            System.err.println("Error al obtener el espacio disponible de la habitación: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void insertarHabitacion(Habitacion habitacion) {
        try {
            habitacionDAO.insertarHabitacion(habitacion);
        } catch (AccessDataException e) {
            System.err.println("Error al insertar la habitación: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
