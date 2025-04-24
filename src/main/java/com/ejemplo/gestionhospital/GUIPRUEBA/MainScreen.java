package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

class MainScreen extends JPanel {
    private PatientsScreen patientsScreen;

    public void setPatientsScreen(PatientsScreen screen) {
        this.patientsScreen = screen;
    }

    public MainScreen(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Menú principal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JButton roomsBtn = new JButton("🏠 Habitaciones");
        JButton bedsBtn = new JButton("🛏️ Camas");
        JButton registerPatientBtn = new JButton("➕ Registrar Paciente");
        JButton showPatientsBtn = new JButton("📋 Mostrar Pacientes");

        for (JButton btn : new JButton[]{roomsBtn, bedsBtn, registerPatientBtn, showPatientsBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
            add(btn);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }

        roomsBtn.addActionListener(e -> cardLayout.show(mainPanel, "rooms"));
        bedsBtn.addActionListener(e -> cardLayout.show(mainPanel, "beds"));
        registerPatientBtn.addActionListener(e -> {
            JTextField nombre = new JTextField();
            JTextField apellidos = new JTextField();
            JTextField dni = new JTextField();
            String[] niveles = {"Baja", "Media", "Alta", "Crítica"};
            JComboBox<String> gravedad = new JComboBox<>(niveles);

            JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
            panel.add(new JLabel("Nombre:")); panel.add(nombre);
            panel.add(new JLabel("Apellidos:")); panel.add(apellidos);
            panel.add(new JLabel("DNI:")); panel.add(dni);
            panel.add(new JLabel("Gravedad:")); panel.add(gravedad);

            int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String info = "ID: " + UUID.randomUUID().toString().substring(0, 8) +
                        " | Nombre: " + nombre.getText() +
                        " | Apellidos: " + apellidos.getText() +
                        " | DNI: " + dni.getText() +
                        " | Gravedad: " + gravedad.getSelectedItem();
                patientsScreen.addPatient(info);
                JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.");
            }
        });

        showPatientsBtn.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
    }
}

