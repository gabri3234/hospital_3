package com.ejemplo.gestionhospital.model;

public class Cama {

    private int id;
    private int habitacion_id;
    private int paciente_id;
    private String estado;

    public Cama(int id, int habitacion_id, String estado, int paciente_id) {
        setHabitacion_id(habitacion_id);
        setId(id);
        setEstado(estado);
        setPaciente_id(paciente_id);

    }

    public Cama(){

    }

    public Cama(int habitacion_id) {
        setHabitacion_id(habitacion_id);
    }

    @Override
    public String toString() {
        return "● Cama " + id + " Habitacion: " + habitacion_id + " Estado: " + estado + " Paciente: " + paciente_id;
    }

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
