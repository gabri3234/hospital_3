package com.ejemplo.gestionhospital.GUIPRUEBA;

import javax.swing.*;
import java.awt.*;

class LoginScreen extends JPanel {
    public LoginScreen(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        JLabel title = new JLabel("Bienvenido a HospitalFlow", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        formPanel.add(new JLabel("Usuario:"));
        JTextField userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Contraseña:"));
        JPasswordField passField = new JPasswordField();
        formPanel.add(passField);

        formPanel.add(new JLabel());
        JButton loginBtn = new JButton("Iniciar sesión");
        formPanel.add(loginBtn);

        add(formPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));
    }
}
