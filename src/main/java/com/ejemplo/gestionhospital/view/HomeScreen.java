package com.ejemplo.gestionhospital.view;

import javax.swing.*;
import java.awt.*;

class HomeScreen extends JPanel {

    private JButton roomsBtn;
    private JButton bedsBtn;
    private JButton patientsBtn;


    public HomeScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        initializeButtons();

        patientsBtn.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
        roomsBtn.addActionListener(e -> cardLayout.show(mainPanel, "rooms"));
        bedsBtn.addActionListener(e -> cardLayout.show(mainPanel, "beds"));
    }

    private void initializePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Menú principal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private void initializeButtons(){
        roomsBtn = new JButton("🏠 Habitaciones");
        bedsBtn = new JButton("🛏️ Camas");
        patientsBtn = new JButton("📋 Pacientes");


        for (JButton btn : new JButton[]{roomsBtn, bedsBtn, patientsBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
            add(btn);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }


    }

}

