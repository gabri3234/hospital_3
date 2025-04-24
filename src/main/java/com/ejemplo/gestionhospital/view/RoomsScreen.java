package com.ejemplo.gestionhospital.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

class RoomsScreen extends JPanel {
    private JTextArea area;
    private java.util.List<String> rooms;
    private Map<String, Integer> roomCapacities = new HashMap<>();

    public RoomsScreen(JPanel mainPanel, CardLayout cardLayout) {
        rooms = new ArrayList<>();
        rooms.add("Habitación 1 Capacidad: 5"); roomCapacities.put("Habitación 1", 5);
        rooms.add("Habitación 2 Capacidad: 3"); roomCapacities.put("Habitación 2", 3);
        rooms.add("Habitación 3 Capacidad: 2"); roomCapacities.put("Habitación 3", 2);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        updateTextArea();
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("⬅️ Atrás");
        JButton addRoomBtn = new JButton("➕ Añadir Habitación");
        bottomPanel.add(backBtn);
        bottomPanel.add(addRoomBtn);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addRoomBtn.addActionListener(e -> showAddRoomDialog());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showAddRoomDialog() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántas camas tiene la nueva habitación?", "Nueva Habitación", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int beds = Integer.parseInt(input);
                String room = "Habitación " + (rooms.size() + 1);
                rooms.add(room + " Capacidad: " + beds);
                roomCapacities.put(room, beds);
                updateTextArea();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTextArea() {
        area.setText(String.join("\n", rooms));
    }

    public java.util.List<String> getRoomNames() {
        java.util.List<String> names = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            names.add("Habitación " + (i + 1));
        }
        return names;
    }

    public Map<String, Integer> getRoomCapacities() {
        return roomCapacities;
    }
}

