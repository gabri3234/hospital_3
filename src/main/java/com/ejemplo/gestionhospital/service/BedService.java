package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.CamaDAO;
import com.ejemplo.gestionhospital.dao.ConexionDB;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;

import java.util.List;

public class BedService {
    private final CamaDAO camaDAO;

    public BedService() {
        camaDAO = new CamaDAO(ConexionDB.getDataSource());
    }

    public void agregarNCamas(int cantidad, int habitacion) {
        Cama cama = new Cama(habitacion);
        try {
            for (int i = 0; i < cantidad; i++) {
                camaDAO.insertarCama(cama);
            }
        } catch (AccessDataException e) {
            System.err.println("Error al insertar cama: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Cama> obtenerCamasLibres() {
        try {
            return camaDAO.obtenerCamasLibres();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener las camas libres: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Cama> obtenerCamas() {
        try {
            return camaDAO.obtenerCamas();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener las camas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void asignarCamaPaciente(int idPaciente, int idCama) {
        try {
            camaDAO.asignarCamaPaciente(idPaciente, idCama);
        } catch (AccessDataException e) {
            System.err.println("Error al asignar la cama al paciente: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
