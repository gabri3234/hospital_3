package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


class PatientsScreen extends JPanel {
    private JTextArea area;
    private JButton backBtn;

    private List<Paciente> patients;
    private final PacienteDAO pacienteDAO;



    public PatientsScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        pacienteDAO = new PacienteDAO();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                showAllPatients();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
    }

    private void getPatients() {
        patients = new ArrayList<>();
        try {
            patients = pacienteDAO.obtenerPacientes();
        } catch (SQLException e) {
            area.append("No ha sido posible obtener la lista de pacientes.");
            throw new RuntimeException(e);
        }
    }

    private void showAllPatients(){
        getPatients();
        area.setText("");
        for(Paciente p : patients){
            area.append(p.toString());
            area.append("\n");
        }
    }

    private void initializePanel(){
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
        backBtn = new JButton("⬅️ Atrás");
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

    }
}
