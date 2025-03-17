package com.ejemplo.gestionhospital;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // String url = "jdbc:h2:file:./data/hospitaldb";
        Scanner scanner = new Scanner(System.in);

        System.out.println("MAIN AQUI");
/*
        System.out.println("---- REGISTRO DE PACIENTE ----");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Estado de gravedad (1-3): ");
        int gravedad = scanner.nextInt();

        Paciente paciente = new Paciente(nombre, apellido, dni, gravedad);
        paciente.insertarPaciente(url);
        System.out.println("✅ Paciente registrado correctamente.");

        System.out.println("\n---- ASIGNACIÓN DE HABITACIÓN Y CAMA ----");
        List<Integer> habitaciones = Cama.obtenerHabitacionesConCamasDisponibles(url);
*/

        /*
        if (habitaciones.isEmpty()) {
            System.out.println("❌ No hay habitaciones disponibles.");
        } else {
            System.out.println("Habitaciones disponibles: " + habitaciones);
            System.out.print("Seleccione una habitación: ");
            int habitacionElegida = scanner.nextInt();

            List<Integer> camas = Cama.obtenerCamasDisponibles(url, habitacionElegida);
            if (camas.isEmpty()) {
                System.out.println("❌ No hay camas disponibles en esa habitación.");
            } else {
                System.out.println("Camas disponibles: " + camas);
                System.out.print("Seleccione una cama: ");
                int camaElegida = scanner.nextInt();

                Cama cama = new Cama(habitacionElegida, camaElegida, "ocupada");
                cama.asignarCama(url, paciente.getId());
                System.out.println("✅ Paciente asignado a habitación " + habitacionElegida + ", cama " + camaElegida);
            }
        }
*/
        System.out.println("\n---- LISTA DE PACIENTES ----");
        Paciente.obtenerListaPacientes();

        scanner.close();
    }
}


