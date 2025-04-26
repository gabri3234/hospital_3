package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


class PatientsScreen extends JPanel {
    private JTextArea area;
    private JButton backBtn;
    private JButton registerPatientBtn;
    private JButton dischargePatientBtn;

    private List<Paciente> patients;
    private final PacienteDAO pacienteDAO;


    public PatientsScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        pacienteDAO = new PacienteDAO();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllPatients();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        registerPatientBtn.addActionListener(e -> registerPatient());
        dischargePatientBtn.addActionListener(e -> dischargePatient());
    }

    private void getPatients() {
        patients = new ArrayList<>();
        try {
            patients = pacienteDAO.obtenerPacientes();
        } catch (SQLException e) {
            area.append("No ha sido posible obtener la lista de pacientes.");
            throw new RuntimeException(e);
        }
    }

    private void showAllPatients(){
        getPatients();
        area.setText("");
        for(Paciente p : patients){
            area.append(p.toString());
            area.append("\n");
        }
    }

    private void registerPatient(){
        JTextField nombre = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField dni = new JTextField();
        Integer[] niveles = {1,2,3};
        JComboBox<Integer> gravedad = new JComboBox<>(niveles);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Apellidos:")); panel.add(apellidos);
        panel.add(new JLabel("DNI:")); panel.add(dni);
        panel.add(new JLabel("Gravedad (1 Baja 2 Moderada 3 Alta) :")); panel.add(gravedad);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Paciente paciente = new Paciente(
                    nombre.getText(),
                    apellidos.getText(),
                    dni.getText(),
                    (int) gravedad.getSelectedItem()
            );

            PacienteDAO pacienteDAO = new PacienteDAO();

            try{
                pacienteDAO.insertarPaciente(paciente);
            }catch (SQLException e){
                JOptionPane.showMessageDialog(this, "No ha sido posible registrar el paciente.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");
            showAllPatients();

        }
    }

    private void dischargePatient(){

        JComboBox<Paciente> pacientesActuales = new JComboBox<>();

        try {
            List<Paciente> pacientes = pacienteDAO.obtenerPacientes();
            for(Paciente p : pacientes){
                pacientesActuales.addItem(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener la lista de pacientes.");
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(500, 150));
        panel.add(new JLabel("Seleccione paciente:")); panel.add(pacientesActuales);

        int result = JOptionPane.showConfirmDialog(this, panel, "Dar de alta:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if(! new ConfirmDialog(frame).getOption()){
                return;
            }

            Paciente pacienteSeleccionado = (Paciente) pacientesActuales.getSelectedItem();
            try {
                pacienteDAO.eliminarPaciente(pacienteSeleccionado);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible dar el alta.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente dado de alta exitosamente.");
            showAllPatients();
        }

    }

    private void initializePanel(){
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
        registerPatientBtn = new JButton("➕ Registrar Paciente");
        dischargePatientBtn = new JButton("Dar Alta");
        bottomPanel.add(backBtn);
        bottomPanel.add(registerPatientBtn);
        bottomPanel.add(dischargePatientBtn);
        add(bottomPanel, BorderLayout.SOUTH);

    }
}
