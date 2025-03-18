package com.ejemplo.gestionhospital;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Gestión del Hospital ---");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Ver lista de pacientes");
            System.out.println("3. Ver habitaciones con camas disponibles");
            System.out.println("4. Asignar cama a paciente");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir nueva línea

            switch (opcion) {
                case 1:
                    registrarPaciente(scanner);
                    break;
                case 2:
                    Paciente.obtenerListaPacientes();
                    break;
                case 3:
                    mostrarHabitacionesDisponibles();
                    break;
                case 4:
                    asignarCamaPaciente(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarPaciente(Scanner scanner) {
        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido del paciente: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el DNI del paciente: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese el estado de gravedad (1-10): ");
        int gravedad = scanner.nextInt();
        scanner.nextLine();  // Consumir nueva línea

        Paciente paciente = new Paciente(nombre, apellido, dni, gravedad);
        paciente.insertarPaciente();
        System.out.println("Paciente registrado con éxito. ID asignado: " + paciente.getId());
    }

    private static void mostrarHabitacionesDisponibles() {
        List<Integer> habitaciones = Cama.obtenerHabitacionesConCamasDisponibles();
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones con camas disponibles.");
        } else {
            System.out.println("Habitaciones con camas disponibles: " + habitaciones);
        }
    }

    private static void asignarCamaPaciente(Scanner scanner) {
        System.out.print("Ingrese el ID del paciente: ");
        int pacienteId = scanner.nextInt();
        scanner.nextLine();  // Consumir nueva línea

        List<Integer> habitacionesDisponibles = Cama.obtenerHabitacionesConCamasDisponibles();
        if (habitacionesDisponibles.isEmpty()) {
            System.out.println("No hay camas disponibles en ninguna habitación.");
            return;
        }

        System.out.println("Habitaciones disponibles: " + habitacionesDisponibles);
        System.out.print("Seleccione el ID de la habitación: ");
        int habitacionElegida = scanner.nextInt();
        scanner.nextLine();  // Consumir nueva línea

        List<Integer> camasDisponibles = Cama.obtenerCamasDisponibles(habitacionElegida);
        if (camasDisponibles.isEmpty()) {
            System.out.println("No hay camas disponibles en la habitación seleccionada.");
            return;
        }

        System.out.println("Camas disponibles en la habitación " + habitacionElegida + ": " + camasDisponibles);
        System.out.print("Seleccione el ID de la cama: ");
        int camaElegida = scanner.nextInt();
        scanner.nextLine();  // Consumir nueva línea

        Cama cama = new Cama(habitacionElegida, camaElegida, "libre");
        cama.asignarCama(pacienteId);
        System.out.println("Cama asignada correctamente al paciente.");
    }
}

