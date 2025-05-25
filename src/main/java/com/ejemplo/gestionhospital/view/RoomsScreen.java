package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.exception.DeletionNotAllowedException;
import com.ejemplo.gestionhospital.model.Habitacion;
import com.ejemplo.gestionhospital.service.RoomService;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

class RoomsScreen extends JPanel {
    private JTextArea area;
    private List<Habitacion> rooms;
    private JButton backBtn;
    private JButton addRoomBtn;
    private JButton removeRoomBtn;

    private RoomService roomService;

    public RoomsScreen(JPanel mainPanel, CardLayout cardLayout) {

        rooms = new ArrayList<>();
        roomService = new RoomService();

        initializePanel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllRooms();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addRoomBtn.addActionListener(e -> addNewRoom());
        removeRoomBtn.addActionListener(e -> removeRoom());


    }

    private void addNewRoom() {
        String input = JOptionPane.showInputDialog(this, "¿Capacidad de la nueva habitación?", "Nueva Habitación", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {

                int capacidad = Integer.parseInt(input);
                if (capacidad < 1) {
                    throw new InvalidAttributeValueException();
                }

                Habitacion habitacion = new Habitacion(capacidad);
                roomService.insertarHabitacion(habitacion);
                showAllRooms();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible agregar la habitación.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidAttributeValueException e) {
                JOptionPane.showMessageDialog(this, "La capacidad introducida no es valida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeRoom() {

        JComboBox<Habitacion> habitacionesOpciones = new JComboBox<>();

        try {
            List<Habitacion> habitaciones = roomService.obtenerHabitaciones();
            for (Habitacion h : habitaciones) {
                habitacionesOpciones.addItem(h);
            }
        } catch (AccessDataException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener la lista de habitaciones.");
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(500, 150));
        panel.add(new JLabel("Habitaciones:"));
        panel.add(habitacionesOpciones);

        int result = JOptionPane.showConfirmDialog(this, panel, "Eliminar una habitacion:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!new ConfirmDialog(frame).getOption()) {
                return;
            }

            Habitacion habitacionSeleccionada = (Habitacion) habitacionesOpciones.getSelectedItem();
            try {
                roomService.eliminarHabitacion(habitacionSeleccionada);
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible eliminar la habitacion.");
                return;
            } catch (DeletionNotAllowedException e) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar una habitacion que tenga pacientes ingresados.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Habitacion eliminada exitosamente.");
            showAllRooms();
        }

    }

    private void getRooms() {
        rooms = new ArrayList<>();
        try {
            rooms = roomService.obtenerHabitaciones();
        } catch (AccessDataException e) {
            area.append("No ha sido posible obtener la lista de habitaciones.");
            throw new RuntimeException(e);
        }
    }

    private void showAllRooms() {
        getRooms();
        area.setText("");
        for (Habitacion h : rooms) {
            area.append(h.toString());
            area.append("\n");
        }
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backBtn = new JButton("⬅️ Atrás");
        addRoomBtn = new JButton("✚ Añadir Habitación");
        removeRoomBtn = new JButton("\uD83D\uDDD1️ Eliminar Habitacion");

        bottomPanel.add(backBtn);
        bottomPanel.add(addRoomBtn);
        bottomPanel.add(removeRoomBtn);
        add(bottomPanel, BorderLayout.SOUTH);

    }

}

