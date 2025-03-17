package com.ejemplo.gestionhospital;



import java.sql.*;

public class Paciente {
    private String nombre;
    private String apellido;
    private String dni;
    private int estadoGravedad;
    private int id;

    public Paciente(String nombre, String apellido, String dni, int estadoGravedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.estadoGravedad = estadoGravedad;
    }

    // Getter para ID
    public int getId() {
        return id;
    }

    // Métodos para insertar el paciente en la base de datos
    public void insertarPaciente(String url) {
        String query = "INSERT INTO pacientes (nombre, apellido, dni, estado_gravedad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url); // Conexión sin autenticación
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, dni);
            stmt.setInt(4, estadoGravedad);
            stmt.executeUpdate();

            // Obtener el ID del paciente insertado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }
}
