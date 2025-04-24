package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


class PatientsScreen extends JPanel {
    private JTextArea area;
    private List<String> patients = new ArrayList<>();
    private Set<String> assignedPatients = new HashSet<>();

    public PatientsScreen(JPanel mainPanel, CardLayout cardLayout) {
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
        JButton backBtn = new JButton("⬅️ Atrás");
        bottomPanel.add(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addPatient(String patientInfo) {
        patients.add(patientInfo);
        area.append(patientInfo + "\n\n");
    }

    public List<String> getUnassignedPatients() {
        List<String> unassigned = new ArrayList<>();
        for (String p : patients) {
            if (!assignedPatients.contains(p)) {
                unassigned.add(p);
            }
        }
        return unassigned;
    }

    public void addAssignedPatient(String patient) {
        assignedPatients.add(patient);
    }
}
