package com.ejemplo.gestionhospital.PresentationLayer;

import com.ejemplo.gestionhospital.DataLayer.HabitacionDAO;
import com.ejemplo.gestionhospital.BussinesLayer.Habitacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

class RoomsScreen extends JPanel {
    private JTextArea area;
    private List<Habitacion> rooms;
    private JButton backBtn;
    private JButton addRoomBtn;
    private HabitacionDAO habitacionDAO;

    public RoomsScreen(JPanel mainPanel, CardLayout cardLayout) {

        rooms = new ArrayList<>();
        habitacionDAO = new HabitacionDAO();

        initializePanel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllRooms();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addRoomBtn.addActionListener(e -> addNewRoom());


    }

    private void addNewRoom() {
        String input = JOptionPane.showInputDialog(this, "¿Capacidad de la nueva habitación?", "Nueva Habitación", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {

                int capacidad = Integer.parseInt(input);
                Habitacion habitacion = new Habitacion(capacidad);
                habitacionDAO.insertarHabitacion(habitacion);
                showAllRooms();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible agregar la habitación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getRooms() {
        rooms = new ArrayList<>();
        try {
            rooms = habitacionDAO.obtenerHabitaciones();
        } catch (SQLException e) {
            area.append("No ha sido posible obtener la lista de habitaciones.");
            throw new RuntimeException(e);
        }
    }

    private void showAllRooms(){
        getRooms();
        area.setText("");
        for(Habitacion h : rooms){
            area.append(h.toString());
            area.append("\n");
        }
    }

    public java.util.List<String> getRoomNames() {
        java.util.List<String> names = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            names.add("Habitación " + (i + 1));
        }
        return names;
    }

    private void initializePanel(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backBtn = new JButton("⬅️ Atrás");
        addRoomBtn = new JButton("➕ Añadir Habitación");

        bottomPanel.add(backBtn);
        bottomPanel.add(addRoomBtn);
        add(bottomPanel, BorderLayout.SOUTH);

    }

}

