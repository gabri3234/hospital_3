package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;
import com.ejemplo.gestionhospital.service.BedService;
import com.ejemplo.gestionhospital.service.RoomService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

class BedsScreen extends JPanel {
    private List<Cama> beds;

    private JTextArea area;
    private JButton backBtn;
    private JButton addBedBtn;


    private BedService bedService;
    private RoomService roomService;


    public BedsScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        bedService = new BedService();
        roomService = new RoomService();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllBeds();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addBedBtn.addActionListener(e -> addNewBeds());


    }

    private void addNewBeds() {

        JTextField camas = new JTextField();
        JComboBox<Habitacion> habitaciones = new JComboBox<>();
        List<Habitacion> roomOptions;

        try {
            roomOptions = roomService.obtenerHabitaciones();
            for (Habitacion h : roomOptions) {
                habitaciones.addItem(h);
            }
        } catch (AccessDataException e) {
            area.append("No ha sido posible obtener la lista de habitaciones.");
            throw new RuntimeException(e);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(350, 250));
        panel.add(new JLabel("Nuevas camas:"));
        panel.add(camas);
        panel.add(new JLabel("Seleccione habitacion:"));
        panel.add(habitaciones);

        int result = JOptionPane.showConfirmDialog(this, panel, "Añadir camas:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Habitacion habitacionSeleccionada = (Habitacion) habitaciones.getSelectedItem();
            int habitacionid = habitacionSeleccionada.getId();

            int ncamas = Integer.parseInt(camas.getText());
            int spaceAvailable = roomService.obtenerEspacioDisponible(habitacionSeleccionada);

            if (ncamas > spaceAvailable) {
                JOptionPane.showMessageDialog(this, "No es posible añadir las nuevas camas.\nEspacio disponible: " + spaceAvailable, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                bedService.agregarNCamas(ncamas, habitacionid);
            } catch (AccessDataException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible añadir las nuevas camas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            showAllBeds();
        }
    }

    private void showAllBeds() {
        getBeds();
        area.setText("");
        for (Cama c : beds) {
            area.append(c.toString());
            area.append("\n");
        }
    }

    private void getBeds() {
        beds = new ArrayList<>();
        try {
            beds = bedService.obtenerCamas();
        } catch (AccessDataException e) {
            area.append("No ha sido posible obtener la lista de camas.");
            throw new RuntimeException(e);
        }
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);


        backBtn = new JButton("⬅️ Atrás");
        addBedBtn = new JButton("➕ Añadir Cama");


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backBtn);
        bottomPanel.add(addBedBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

}
