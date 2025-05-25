package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.model.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class HomeScreen extends JPanel {

    private JButton roomsBtn;
    private JButton bedsBtn;
    private JButton patientsBtn;
    private JButton logoutBtn;


    public HomeScreen(JPanel mainPanel, CardLayout cardLayout) {


        initializePanel();

        if (Sesion.esAdmin()) {
            roomsBtn.addActionListener(e -> cardLayout.show(mainPanel, "rooms"));
        } else {
            patientsBtn.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
            bedsBtn.addActionListener(e -> cardLayout.show(mainPanel, "beds"));
        }

        logoutBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "login");
            Sesion.cerrarSesion();
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                removeAll();
                updatePanel();

            }
        });

    }


    private void updatePanel() {
        JLabel title = new JLabel("Menú principal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));

        if (Sesion.esAdmin()) {
            setButton(roomsBtn);
        } else {
            setButton(bedsBtn);
            setButton(patientsBtn);
        }

        setButton(logoutBtn);
        repaint();
        revalidate();
    }

    private void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));
        roomsBtn = new JButton("🏠 Habitaciones");
        bedsBtn = new JButton("🛏️ Camas");
        patientsBtn = new JButton("📋 Pacientes");
        logoutBtn = new JButton("Cambiar de usuario");
    }

    private void setButton(JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 40));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(btn);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }

}

