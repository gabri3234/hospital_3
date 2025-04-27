package com.ejemplo.gestionhospital.view;

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;

import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame {

    public Frame() {

        setLookAndFeel();

        setTitle("🏥 HospitalFlow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        LoginScreen login = new LoginScreen(mainPanel, cardLayout);
        HomeScreen home = new HomeScreen(mainPanel, cardLayout);
        RoomsScreen rooms = new RoomsScreen(mainPanel, cardLayout);
        PatientsScreen patients = new PatientsScreen(mainPanel, cardLayout);
        BedsScreen beds = new BedsScreen(mainPanel, cardLayout);

        mainPanel.add(login, "login");
        mainPanel.add(home, "home");
        mainPanel.add(rooms, "rooms");
        mainPanel.add(beds, "beds");
        mainPanel.add(patients, "patients");

        add(mainPanel);
        setVisible(true);
    }


    private void setLookAndFeel(){
        try {
            UIManager.put("Button.arc", 10);
            FlatCarbonIJTheme.setup();
            UIManager.setLookAndFeel(new FlatCarbonIJTheme());

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}


