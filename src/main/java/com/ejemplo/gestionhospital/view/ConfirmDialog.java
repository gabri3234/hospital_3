package com.ejemplo.gestionhospital.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmDialog extends JDialog {


    private boolean option;

    public ConfirmDialog(JFrame owner) {
        super(owner, "Confirmación", true); // modal = true

        JLabel label = new JLabel("Estas seguro?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        // Colores pastel
        Color verdePastel = new Color(129, 199, 132);
        Color rojoPastel = new Color(239, 154, 154);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBackground(verdePastel);
        botonAceptar.setForeground(Color.BLACK);
        botonAceptar.setFocusPainted(false);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBackground(rojoPastel);
        botonCancelar.setForeground(Color.BLACK);
        botonCancelar.setFocusPainted(false);

        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = true;
                dispose();
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = false;
                dispose();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonAceptar);
        panelBotones.add(botonCancelar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(label, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public boolean getOption(){
        return option;
    }
}


