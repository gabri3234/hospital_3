package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.CamaDAO;
import com.ejemplo.gestionhospital.dao.HabitacionDAO;
import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

class BedsScreen extends JPanel {
    private List<Cama> beds;

    private JTextArea area;
    private JButton backBtn;
    private JButton addBedBtn;
    private HabitacionDAO habitacionDAO;
    private CamaDAO camaDAO;

    public BedsScreen(JPanel mainPanel, CardLayout cardLayout) {

        habitacionDAO = new HabitacionDAO();
        camaDAO = new CamaDAO();
        initializePanel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllBeds();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addBedBtn.addActionListener(e -> addNewBeds());

    }

    private void addNewBeds(){

        JTextField camas = new JTextField();
        JComboBox<Habitacion> habitaciones = new JComboBox<>();
        List<Habitacion> roomOptions;

        try {
            roomOptions = habitacionDAO.obtenerHabitaciones();
            for(Habitacion h : roomOptions){
                habitaciones.addItem(h);
            }
        } catch (SQLException e) {
            area.append("No ha sido posible obtener la lista de habitaciones.");
            throw new RuntimeException(e);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(350, 250));
        panel.add(new JLabel("Nuevas camas:")); panel.add(camas);
        panel.add(new JLabel("Seleccione habitacion:")); panel.add(habitaciones);

        int result = JOptionPane.showConfirmDialog(this, panel, "Añadir camas:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Habitacion habitacionSeleccionada = (Habitacion) habitaciones.getSelectedItem();
            int ncamas = Integer.parseInt(camas.getText());
            int spaceAvailable = getRoomSpaceAvailable(habitacionSeleccionada);

            if(ncamas > spaceAvailable){
                JOptionPane.showMessageDialog(this, "No es posible añadir las nuevas camas.\nEspacio disponible: " + spaceAvailable, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                addNBedsToRoom(ncamas, habitacionSeleccionada);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible añadir las nuevas camas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            showAllBeds();
        }
    }

    private void addNBedsToRoom(int n, Habitacion habitacion) throws SQLException {
        Cama cama = new Cama(habitacion.getId());
        for(int i = 0; i < n; i++){
            camaDAO.insertarCama(cama);
        }
    }

    private void showAllBeds(){
        getBeds();
        area.setText("");
        for(Cama c : beds){
            area.append(c.toString());
            area.append("\n");
        }
    }

    private void getBeds() {
        beds = new ArrayList<>();
        try {
            beds = camaDAO.obtenerCamas();
        } catch (SQLException e) {
            area.append("No ha sido posible obtener la lista de camas.");
            throw new RuntimeException(e);
        }
    }

    private int getRoomSpaceAvailable(Habitacion habitacion) {

        int capacidadMax = habitacion.getCapacidad();

        try {
            int ocupacion = camaDAO.obtenerCamasHabitacionN(habitacion).size();
            return capacidadMax - ocupacion;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener el numero de camas de la habitacion.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

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
        addBedBtn = new JButton("➕ Añadir Cama");
        bottomPanel.add(backBtn);
        bottomPanel.add(addBedBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    //    private void addNewBeds() {
//
//        List<Habitacion> roomOptions;
//        try {
//            roomOptions = habitacionDAO.obtenerHabitaciones();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
//        panel.setPreferredSize(new Dimension(400, 300));
//        JTextField camas = new JTextField();
//        panel.add(new JLabel("Nuevas camas:")); panel.add(camas);
//
//
//        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
//        String room = (String) JOptionPane.showInputDialog(this, "Selecciona habitación:", "Añadir Cama", JOptionPane.QUESTION_MESSAGE, null, roomOptions.toArray(new String[0]), null);
//        if (room != null) {
//            Map<String, Integer> capacities = roomsScreen.getRoomCapacities();
//            int capacity = capacities.getOrDefault(room, 0);
//            int currentPatients = bedsMap.getOrDefault(room, new ArrayList<>()).size();
//
//            if (currentPatients >= capacity) {
//                JOptionPane.showMessageDialog(this, "No se pueden añadir más pacientes. Capacidad máxima alcanzada.", "Capacidad completa", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            List<String> unassigned = patientsScreen.getUnassignedPatients();
//            if (unassigned.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "No hay pacientes sin cama asignada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//                return;
//            }
//
//            String patient = (String) JOptionPane.showInputDialog(this, "Selecciona paciente:", "Asignar paciente a cama", JOptionPane.QUESTION_MESSAGE, null, unassigned.toArray(), null);
//            if (patient != null && !patient.isEmpty()) {
//                bedsMap.computeIfAbsent(room, k -> new ArrayList<>()).add(patient);
//                patientsScreen.addAssignedPatient(patient);
//                updateTextArea();
//            }
//        }
//    }

}
