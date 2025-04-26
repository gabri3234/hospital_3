package com.ejemplo.gestionhospital.model;

import com.ejemplo.gestionhospital.dao.ConexionDB;

import java.sql.*;

public class Cama {

    private int id;
    private int habitacion_id;
    private int paciente_id;
    private String estado;

    public Cama(int id, int habitacion_id, String estado, int paciente_id) {
        this.habitacion_id = habitacion_id;
        this.id = id;
        this.estado = estado;
        this.paciente_id = paciente_id;

    }

    public Cama(int habitacion_id){
        this.habitacion_id = habitacion_id;
    }

    @Override
    public String toString(){
        return "● Cama " + id + " Habitacion: " + habitacion_id + " Estado: " + estado + " Paciente: " + paciente_id;
    }

//    public void asignarPaciente(int pacienteId) {
//        String verificarQuery = "SELECT estado FROM camas WHERE habitacion_id = ? AND id = ?";
//        String asignarQuery = "UPDATE camas SET estado = 'ocupada', paciente_id = ? WHERE habitacion_id = ? AND id = ? AND estado = 'libre'";
//
//        try (Connection conn = ConexionDB.getConnection();
//             PreparedStatement verificarStmt = conn.prepareStatement(verificarQuery);
//             PreparedStatement asignarStmt = conn.prepareStatement(asignarQuery)) {
//
//            verificarStmt.setInt(1, this.habitacion_id);
//            verificarStmt.setInt(2, this.id);
//
//            ResultSet rs = verificarStmt.executeQuery();
//            if (rs.next() && rs.getString("estado").equals("libre")) {
//                asignarStmt.setInt(1, pacienteId);
//                asignarStmt.setInt(2, this.habitacion_id);
//                asignarStmt.setInt(3, this.id);
//                asignarStmt.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // SETTERS AND GETTERS -----------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHabitacion_id() {
        return habitacion_id;
    }

    public void setHabitacion_id(int habitacion_id) {
        this.habitacion_id = habitacion_id;
    }

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int paciente_id) {
        this.paciente_id = paciente_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
