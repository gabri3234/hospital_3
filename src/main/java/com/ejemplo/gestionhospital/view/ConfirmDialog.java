package com.ejemplo.gestionhospital.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmDialog extends JDialog {

    private JButton acceptBtn;
    private JButton cancelBtn;

    private boolean option;

    public ConfirmDialog(JFrame owner) {
        super(owner, "Confirmación", true);

        JLabel label = new JLabel("Estas seguro?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        initializeButtons();


        JPanel panelBotones = new JPanel();
        panelBotones.add(acceptBtn);
        panelBotones.add(cancelBtn);

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

    private void initializeButtons(){

        Color green = new Color(129, 199, 132);
        Color red = new Color(239, 154, 154);

        acceptBtn = new JButton("Aceptar");
        acceptBtn.setBackground(green);
        acceptBtn.setForeground(Color.BLACK);
        acceptBtn.setFocusPainted(false);

        cancelBtn = new JButton("Cancelar");
        cancelBtn.setBackground(red);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);

        acceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = true;
                dispose();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = false;
                dispose();
            }
        });
    }

}


