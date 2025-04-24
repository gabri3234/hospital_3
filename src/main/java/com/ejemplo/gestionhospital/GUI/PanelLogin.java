package com.ejemplo.gestionhospital.GUI;

import com.ejemplo.gestionhospital.dao.ConexionDB;

import javax.swing.*;
import java.awt.*;

public class PanelLogin extends JPanel {

    private Frame frame;

    public PanelLogin(Frame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        inicializarContenido();
        setVisible(true);
    }

    private void inicializarContenido(){
        JButton BtnIniciarSesion = new JButton("Iniciar Sesion");
        BtnIniciarSesion.addActionListener(e -> iniciarSesion());
        add(BtnIniciarSesion);
    }

    private void iniciarSesion(){
        JTextField usuario = new JTextField();
        JPasswordField password = new JPasswordField();

        Object[] campos = {
                "Usuario:", usuario,
                "Contraseña:", password
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Iniciar Sesion", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            ConexionDB.setUsername(usuario.getText());
            ConexionDB.setPassword(new String(password.getPassword()));
        }

        if(ConexionDB.getConnection() == null){
            JOptionPane.showMessageDialog(this, "Usuario o constraseña invalidos");
            return;
        }else{
            frame.cambiarPanelAcciones();
        }

    }


}
