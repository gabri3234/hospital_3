package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class HomeScreen extends JPanel {

    private PatientsScreen patientsScreen;
    private JButton roomsBtn;
    private JButton bedsBtn;
    private JButton registerPatientBtn;
    private JButton showPatientsBtn;


    public void setPatientsScreen(PatientsScreen screen) {
        this.patientsScreen = screen;
    }

    public HomeScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        initializeButtons();


        showPatientsBtn.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
        roomsBtn.addActionListener(e -> cardLayout.show(mainPanel, "rooms"));
        bedsBtn.addActionListener(e -> cardLayout.show(mainPanel, "beds"));
        registerPatientBtn.addActionListener(e -> registerPatient());


    }

    private void registerPatient(){
        JTextField nombre = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField dni = new JTextField();
        String[] niveles = {"Baja", "Media", "Alta", "Crítica"};
        JComboBox<String> gravedad = new JComboBox<>(niveles);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Apellidos:")); panel.add(apellidos);
        panel.add(new JLabel("DNI:")); panel.add(dni);
        panel.add(new JLabel("Gravedad:")); panel.add(gravedad);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Paciente paciente = new Paciente(
                    nombre.getText(),
                    apellidos.getText(),
                    dni.getText(),
                    5
            );

            PacienteDAO pacienteDAO = new PacienteDAO();

            try{
                pacienteDAO.insertarPaciente(paciente);
            }catch (SQLException e){
                JOptionPane.showMessageDialog(this, "No ha sido posible registrar el paciente.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");

            patientsScreen.addPatient(paciente);
        }
    }

    private void initializePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Menú principal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private void initializeButtons(){
        roomsBtn = new JButton("🏠 Habitaciones");
        bedsBtn = new JButton("🛏️ Camas");
        registerPatientBtn = new JButton("➕ Registrar Paciente");
        showPatientsBtn = new JButton("📋 Mostrar Pacientes");

        for (JButton btn : new JButton[]{roomsBtn, bedsBtn, registerPatientBtn, showPatientsBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
            add(btn);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }


    }

}

