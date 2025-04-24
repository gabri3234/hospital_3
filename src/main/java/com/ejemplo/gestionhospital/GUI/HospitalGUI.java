package com.ejemplo.gestionhospital.GUI;

import com.ejemplo.gestionhospital.Cama;
import com.ejemplo.gestionhospital.Habitacion;
import com.ejemplo.gestionhospital.Hospital;
import com.ejemplo.gestionhospital.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class HospitalGUI extends JFrame {

    public HospitalGUI() {
        setTitle("Gestión del Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnRegistrarPaciente = new JButton("Registrar nuevo paciente");
        JButton btnVerPacientes = new JButton("Ver lista de pacientes");
        JButton btnVerHabitaciones = new JButton("Ver habitaciones disponibles");
        JButton btnAsignarCama = new JButton("Asignar cama a paciente");
        JButton btnSalir = new JButton("Salir");

        btnRegistrarPaciente.addActionListener(e -> this.registrarPaciente());
        btnVerPacientes.addActionListener(e -> Hospital.obtenerListaPacientes());
        btnVerHabitaciones.addActionListener(e -> mostrarHabitaciones());
        btnAsignarCama.addActionListener(this::asignarCama);
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnRegistrarPaciente);
        panel.add(btnVerPacientes);
        panel.add(btnVerHabitaciones);
        panel.add(btnAsignarCama);
        panel.add(btnSalir);

        add(panel);
        setVisible(true);
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
                paciente.insertarPaciente();
                JOptionPane.showMessageDialog(this, "Paciente registrado. ID asignado: " + paciente.getId());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gravedad inválida");
            }
        }
    }


    private void mostrarHabitaciones() {
        List<Integer> disponibles = Hospital.obtenerHabitacionesConCamasDisponibles();
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay habitaciones con camas disponibles.");
        } else {
            JOptionPane.showMessageDialog(this, "Habitaciones disponibles: " + disponibles);
        }
    }

    private void asignarCama(ActionEvent e) {
        try {
            String inputPaciente = JOptionPane.showInputDialog(this, "Ingrese el ID del paciente:");
            if (inputPaciente == null) return;

            int pacienteId = Integer.parseInt(inputPaciente);

            List<Integer> habitaciones = Hospital.obtenerHabitacionesConCamasDisponibles();
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
            camaLibre.asignarCama(pacienteId);
            JOptionPane.showMessageDialog(this, "Cama asignada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al asignar cama.");
        }
    }

}
