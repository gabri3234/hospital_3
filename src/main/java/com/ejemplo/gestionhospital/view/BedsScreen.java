package com.ejemplo.gestionhospital.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class BedsScreen extends JPanel {
    private JTextArea area;
    private Map<String, List<String>> bedsMap;
    private RoomsScreen roomsScreen;
    private PatientsScreen patientsScreen;

    public BedsScreen(JPanel mainPanel, CardLayout cardLayout, RoomsScreen roomsScreen, PatientsScreen patientsScreen) {
        this.roomsScreen = roomsScreen;
        this.patientsScreen = patientsScreen;
        bedsMap = new LinkedHashMap<>();
        for (String room : roomsScreen.getRoomNames()) {
            bedsMap.put(room, new ArrayList<>());
        }

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        updateTextArea();
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("⬅️ Atrás");
        JButton addBedBtn = new JButton("➕ Añadir Cama");
        bottomPanel.add(backBtn);
        bottomPanel.add(addBedBtn);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addBedBtn.addActionListener(e -> showAddBedDialog());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showAddBedDialog() {
        java.util.List<String> roomOptions = roomsScreen.getRoomNames();
        String room = (String) JOptionPane.showInputDialog(this, "Selecciona habitación:", "Añadir Cama", JOptionPane.QUESTION_MESSAGE, null, roomOptions.toArray(new String[0]), null);
        if (room != null) {
            Map<String, Integer> capacities = roomsScreen.getRoomCapacities();
            int capacity = capacities.getOrDefault(room, 0);
            int currentPatients = bedsMap.getOrDefault(room, new ArrayList<>()).size();

            if (currentPatients >= capacity) {
                JOptionPane.showMessageDialog(this, "No se pueden añadir más pacientes. Capacidad máxima alcanzada.", "Capacidad completa", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> unassigned = patientsScreen.getUnassignedPatients();
            if (unassigned.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay pacientes sin cama asignada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String patient = (String) JOptionPane.showInputDialog(this, "Selecciona paciente:", "Asignar paciente a cama", JOptionPane.QUESTION_MESSAGE, null, unassigned.toArray(), null);
            if (patient != null && !patient.isEmpty()) {
                bedsMap.computeIfAbsent(room, k -> new ArrayList<>()).add(patient);
                patientsScreen.addAssignedPatient(patient);
                updateTextArea();
            }
        }
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        for (String room : bedsMap.keySet()) {
            sb.append(room).append("\n");
            List<String> patients = bedsMap.get(room);
            if (patients.isEmpty()) {
                sb.append("  0 camas\n");
            } else {
                for (int i = 0; i < patients.size(); i++) {
                    sb.append("  Paciente ").append(i + 1).append(": ").append(patients.get(i)).append("\n");
                }
            }
            sb.append("\n");
        }
        area.setText(sb.toString());
    }
}
