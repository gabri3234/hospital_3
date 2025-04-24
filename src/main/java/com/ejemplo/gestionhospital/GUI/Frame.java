package com.ejemplo.gestionhospital.GUI;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private PanelLogin panelLogin = new PanelLogin(this);
    private PanelAcciones panelAcciones = new PanelAcciones();

    public Frame(){

        setTitle("Hospital Flow Manager");
        setLocationRelativeTo(null);
        setSize(800,500);
        setResizable(false);
        setVisible(true);
    }


    public void cambiarPanelAcciones(){
        setContentPane(panelAcciones);
        revalidate();
        repaint();
    }




}
