package com.ejemplo.gestionhospital.view;

import com.ejemplo.gestionhospital.service.LoginService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class LoginScreen extends JPanel {

    private JButton loginBtn;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginService loginService;

    public LoginScreen(JPanel mainPanel, CardLayout cardLayout) {

        initializePanel();
        initializeFields();

        loginService = new LoginService();
        loginBtn.addActionListener(e -> login(mainPanel, cardLayout));
    }

    private void login(JPanel mainPanel,CardLayout cardLayout){

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if(!loginService.autenticar(username, password)){
            JOptionPane.showMessageDialog(this, "Usuario o contraseña invalidos");
        }else{
            cardLayout.show(mainPanel, "home");
        }

        usernameField.setText("");
        passwordField.setText("");
    }


    private void initializePanel(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        JLabel title = new JLabel("Bienvenido a HospitalFlow", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
    }

    private void initializeFields(){
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        formPanel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel());
        loginBtn = new JButton("Iniciar sesión");
        formPanel.add(loginBtn);
        add(formPanel, BorderLayout.CENTER);
    }
}
