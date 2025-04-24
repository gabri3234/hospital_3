package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.HabitacionDAO;
import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelAcciones extends JPanel {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private HabitacionDAO habitacionDAO = new HabitacionDAO();

    public PanelAcciones() {

        setLayout(new GridLayout(5, 1, 10, 10));
        inicializarBotones();
        setVisible(true);

    }

    private void inicializarBotones(){
        JButton btnRegistrarPaciente = new JButton("Registrar nuevo paciente");
        JButton btnVerPacientes = new JButton("Ver lista de pacientes");
        JButton btnVerHabitaciones = new JButton("Ver habitaciones disponibles");
        JButton btnAsignarCama = new JButton("Asignar cama a paciente");
        JButton btnSalir = new JButton("Salir");

        btnRegistrarPaciente.addActionListener(e -> registrarPaciente());
        btnVerPacientes.addActionListener(e -> {
            try {
                pacienteDAO.obtenerPacientes();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnVerHabitaciones.addActionListener(e -> mostrarHabitaciones());
        btnAsignarCama.addActionListener(e -> asignarCama());
        btnSalir.addActionListener(e -> System.exit(0));

        add(btnRegistrarPaciente);
        add(btnVerPacientes);
        add(btnVerHabitaciones);
        add(btnAsignarCama);
        add(btnSalir);
    }

    public void registrarPaciente() {
        JTextField nombre = new JTextField();
        JTextField apellido = new JTextField();
        JTextField dni = new JTextField();
        JTextField gravedad = new JTextField();

        Object[] campos = {
                "Nombre:", nombre,
                "Apellido:", apellido,
                "DNI:", dni,
                "Gravedad (1-10):", gravedad
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int g = Integer.parseInt(gravedad.getText());
                Paciente paciente = new Paciente(nombre.getText(), apellido.getText(), dni.getText(), g);
                pacienteDAO.insertarPaciente(paciente);
                JOptionPane.showMessageDialog(this, "Paciente registrado. ID asignado: " + paciente.getId());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gravedad inválida");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void mostrarHabitaciones() {
        List<Integer> disponibles = habitacionDAO.obtenerHabitacionesDisponibles();
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay habitaciones con camas disponibles.");
        } else {
            JOptionPane.showMessageDialog(this, "Habitaciones disponibles: " + disponibles);
        }
    }

    private void asignarCama() {
        try {
            String inputPaciente = JOptionPane.showInputDialog(this, "Ingrese el ID del paciente:");
            if (inputPaciente == null) return;

            int pacienteId = Integer.parseInt(inputPaciente);

            List<Integer> habitaciones = habitacionDAO.obtenerHabitacionesDisponibles();
            if (habitaciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay camas disponibles.");
                return;
            }

            Integer habitacion = (Integer) JOptionPane.showInputDialog(this, "Selecciona habitación:",
                    "Habitaciones", JOptionPane.QUESTION_MESSAGE, null,
                    habitaciones.toArray(), habitaciones.get(0));

            List<Integer> camas = Habitacion.obtenerCamasDisponibles(habitacion);
            if (camas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay camas libres en esta habitación.");
                return;
            }

            Integer numeroCama = (Integer) JOptionPane.showInputDialog(this, "Selecciona cama:",
                    "Camas", JOptionPane.QUESTION_MESSAGE, null,
                    camas.toArray(), camas.get(0));

            Cama camaLibre = new Cama(habitacion, numeroCama, "libre");
            camaLibre.asignarPaciente(pacienteId);
            JOptionPane.showMessageDialog(this, "Cama asignada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al asignar cama.");
        }
    }

}
