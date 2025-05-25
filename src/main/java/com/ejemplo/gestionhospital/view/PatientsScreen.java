package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.ConexionDB;
import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Paciente;
import com.ejemplo.gestionhospital.service.BedService;
import com.ejemplo.gestionhospital.service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;


class PatientsScreen extends JPanel {
    private final PatientService patientService;
    private final BedService bedService;

    private JTextArea area;
    private JButton backBtn;
    private JButton registerPatientBtn;
    private JButton dischargePatientBtn;
    private JButton assignBedToPatientBtn;


    public PatientsScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        patientService = new PatientService();
        bedService = new BedService();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllPatients();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        registerPatientBtn.addActionListener(e -> registerPatient());
        dischargePatientBtn.addActionListener(e -> dischargePatient());
        assignBedToPatientBtn.addActionListener(e -> assignBedToPatient());

    }

    private void registerPatient() {
        JTextField nombre = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField dni = new JTextField();
        Integer[] niveles = {1, 2, 3};
        JComboBox<Integer> gravedad = new JComboBox<>(niveles);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidos);
        panel.add(new JLabel("DNI:"));
        panel.add(dni);
        panel.add(new JLabel("Gravedad (1 Baja 2 Moderada 3 Alta) :"));
        panel.add(gravedad);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Paciente paciente = new Paciente(nombre.getText(), apellidos.getText(), dni.getText(), (int) gravedad.getSelectedItem());

            PacienteDAO pacienteDAO = new PacienteDAO(ConexionDB.getDataSource());

            try {
                pacienteDAO.insertarPaciente(paciente);
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible registrar el paciente.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");
            showAllPatients();

        }
    }

    private void dischargePatient() {

        JComboBox<Paciente> pacientesActuales = new JComboBox<>();

        try {
            List<Paciente> pacientes = patientService.obtenerPacientesIngresados();
            for (Paciente p : pacientes) {
                pacientesActuales.addItem(p);
            }
        } catch (AccessDataException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener la lista de pacientes.");
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(500, 150));
        panel.add(new JLabel("Pacientes ingresados:"));
        panel.add(pacientesActuales);

        int result = JOptionPane.showConfirmDialog(this, panel, "Dar de alta:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!new ConfirmDialog(frame).getOption()) {
                return;
            }

            Paciente pacienteSeleccionado = (Paciente) pacientesActuales.getSelectedItem();
            try {
                patientService.eliminarPaciente(pacienteSeleccionado);
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible dar el alta.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente dado de alta exitosamente.");
            showAllPatients();
        }

    }

    private void assignBedToPatient() {

        JComboBox<Paciente> pacientesSinIngresar = new JComboBox<>();
        JComboBox<Cama> camasLibres = new JComboBox<>();

        try {
            List<Paciente> pacientes = patientService.obtenerPacientesNoIngresados();
            List<Cama> camas = bedService.obtenerCamasLibres();

            for (Paciente p : pacientes) {
                pacientesSinIngresar.addItem(p);
            }

            for (Cama c : camas) {
                camasLibres.addItem(c);
            }

        } catch (AccessDataException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener la lista de pacientes y camas.");
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(500, 150));
        panel.add(new JLabel("Pacientes sin cama:"));
        panel.add(pacientesSinIngresar);
        panel.add(new JLabel("Camas libres:"));
        panel.add(camasLibres);

        int result = JOptionPane.showConfirmDialog(this, panel, "Asignar cama:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Paciente pacienteSeleccionado = (Paciente) pacientesSinIngresar.getSelectedItem();
            Cama camaSeleccionada = (Cama) camasLibres.getSelectedItem();

            try {
                bedService.asignarCamaPaciente(pacienteSeleccionado.getId(), camaSeleccionada.getId());
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible asignarle la cama.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Cama asignada exitosamente.");
        }

    }

    private void showAllPatients() {

        area.setText("");
        area.append("Pacientes Ingresados en cama: \n\n");
        for (Paciente p : patientService.obtenerPacientesIngresados()) {
            area.append(p.toString());
            area.append("\n");
        }
        area.append("\nPacientes NO Ingresados en cama: \n\n");
        for (Paciente p : patientService.obtenerPacientesNoIngresados()) {
            area.append(p.toString());
            area.append("\n");
        }

    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("📋 Lista de Pacientes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backBtn = new JButton("⬅️ Atrás");
        registerPatientBtn = new JButton("➕ Registrar");
        dischargePatientBtn = new JButton("Dar Alta");
        assignBedToPatientBtn = new JButton("Asignar Cama");


        bottomPanel.add(backBtn);
        bottomPanel.add(registerPatientBtn);
        bottomPanel.add(dischargePatientBtn);
        bottomPanel.add(assignBedToPatientBtn);
        add(bottomPanel, BorderLayout.SOUTH);

    }
}
