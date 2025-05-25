package com.ejemplo.gestionhospital.PresentationLayer;

import com.ejemplo.gestionhospital.DataLayer.CamaDAO;
import com.ejemplo.gestionhospital.DataLayer.HabitacionDAO;
import com.ejemplo.gestionhospital.DataLayer.PacienteDAO;
import com.ejemplo.gestionhospital.BussinesLayer.Cama;
import com.ejemplo.gestionhospital.BussinesLayer.Habitacion;
import com.ejemplo.gestionhospital.BussinesLayer.Paciente;

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
    private JButton assignBedToPatientBtn;

    private final HabitacionDAO habitacionDAO;
    private final CamaDAO camaDAO;
    private final PacienteDAO pacienteDAO;

    public BedsScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        habitacionDAO = new HabitacionDAO();
        camaDAO = new CamaDAO();
        pacienteDAO = new PacienteDAO();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllBeds();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        addBedBtn.addActionListener(e -> addNewBeds());
        assignBedToPatientBtn.addActionListener(e -> assignBedToPatient());

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
            int spaceAvailable = habitacionDAO.obtenerEspacioDisponible(habitacionSeleccionada);

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

    private void assignBedToPatient(){

        JComboBox<Paciente> pacientesSinIngresar = new JComboBox<>();
        JComboBox<Cama> camasLibres = new JComboBox<>();

        try {
            List<Paciente> pacientes = pacienteDAO.obtenerPacientesNoIngresados();
            List<Cama> camas = camaDAO.obtenerCamasLibres();

            for(Paciente p : pacientes){
                pacientesSinIngresar.addItem(p);
            }

            for(Cama c : camas){
                camasLibres.addItem(c);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible obtener la lista de pacientes y camas.");
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(500, 150));
        panel.add(new JLabel("Pacientes sin cama:")); panel.add(pacientesSinIngresar);
        panel.add(new JLabel("Camas libres:")); panel.add(camasLibres);

        int result = JOptionPane.showConfirmDialog(this, panel, "Asignar cama:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            Paciente pacienteSeleccionado = (Paciente) pacientesSinIngresar.getSelectedItem();
            Cama camaSeleccionada = (Cama) camasLibres.getSelectedItem();

            try {
                camaDAO.asignarCamaPaciente(pacienteSeleccionado.getId(), camaSeleccionada.getId());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "No ha sido posible asignarle la cama.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Cama asignada exitosamente.");
            showAllBeds();
        }

    }

    private void initializePanel(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);


        backBtn = new JButton("⬅️ Atrás");
        addBedBtn = new JButton("➕ Añadir Cama");
        assignBedToPatientBtn = new JButton("Asignar Cama");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backBtn);
        bottomPanel.add(addBedBtn);
        bottomPanel.add(assignBedToPatientBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

}
