package com.ejemplo.gestionhospital;

import com.ejemplo.gestionhospital.GUI.Frame;
import com.ejemplo.gestionhospital.GUI.PanelAcciones;
import com.ejemplo.gestionhospital.GUI.PanelLogin;

public class Main {
    public static void main(String[] args) {

        Frame frame = new Frame();
        frame.add(new PanelLogin(frame));

    }
}

