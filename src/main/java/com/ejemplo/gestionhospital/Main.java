package com.ejemplo.gestionhospital;

public class Main {
    public static void main(String[] args) {
        // URL de conexión a la base de datos H2 sin autenticación
        String url = "jdbc:h2:file:./data/hospitaldb";  // Tu URL

        // Crear y agregar un paciente
        Paciente paciente1 = new Paciente("francisco ", "franco", "12345680A", 2);
        paciente1.insertarPaciente(url);  // Pasar la URL a los métodos

        // Crear y asignar una cama al paciente
        Cama cama1 = new Cama(1, 1, "ocupada"); // Asignamos la cama 1 en habitación 1
        cama1.asignarCama(url, paciente1.getId());  // Pasar la URL aquí también

        System.out.println("Paciente " + paciente1.getNombre() + " " + paciente1.getApellido() + " agregado.");
        System.out.println("Paciente con DNI " + paciente1.getDni() + " asignado a una cama.");
    }
}

