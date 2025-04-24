package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;

public class HospitalFlowUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("🏥 HospitalFlow");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);

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

            frame.add(mainPanel);
            frame.setVisible(true);
            System.out.println("Hospital flow started");
        });
    }
}
