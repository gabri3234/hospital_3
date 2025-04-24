package com.ejemplo.gestionhospital;

import com.ejemplo.gestionhospital.view.Frame;
import com.ejemplo.gestionhospital.view.PanelLogin;

public class Main {
    public static void main(String[] args) {

        Frame frame = new Frame();
        frame.add(new PanelLogin(frame));

    }
}

