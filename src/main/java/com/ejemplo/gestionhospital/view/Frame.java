package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        setTitle("🏥 HospitalFlow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        LoginScreen login = new LoginScreen(mainPanel, cardLayout);
        MainScreen main = new MainScreen(mainPanel, cardLayout);
        RoomsScreen rooms = new RoomsScreen(mainPanel, cardLayout);
        PatientsScreen patients = new PatientsScreen(mainPanel, cardLayout);
        BedsScreen beds = new BedsScreen(mainPanel, cardLayout, rooms, patients);

        main.setPatientsScreen(patients);

        mainPanel.add(login, "login");
        mainPanel.add(main, "main");
        mainPanel.add(rooms, "rooms");
        mainPanel.add(beds, "beds");
        mainPanel.add(patients, "patients");

        add(mainPanel);
        setVisible(true);
    }
}


