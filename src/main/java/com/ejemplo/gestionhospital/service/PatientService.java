package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Paciente;

import java.util.List;

public class PatientService {

    private final PacienteDAO pacienteDAO;

    public PatientService() {
        pacienteDAO = new PacienteDAO();
    }

    public List<Paciente> obtenerPacientesNoIngresados() {
        try {
            return pacienteDAO.obtenerPacientesNoIngresados();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener los pacientes no ingresados: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void eliminarPaciente(Paciente pacienteSeleccionado) {
        try {
            pacienteDAO.eliminarPaciente(pacienteSeleccionado);
        } catch (AccessDataException e) {
            System.err.println("Error al eliminar el paciente: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Paciente> obtenerPacientesIngresados() {
        try {
            return pacienteDAO.obtenerPacientesIngresados();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener los pacientes ingresados: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }


    }

    public List<Paciente> obtenerPacientes() {
        try {
            return obtenerPacientes();
        } catch (AccessDataException e) {
            System.err.println("Error al obtener todos los pacientes: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }


    }
}
