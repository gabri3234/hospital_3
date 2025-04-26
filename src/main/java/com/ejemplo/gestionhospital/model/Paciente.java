package com.ejemplo.gestionhospital.model;

public class Paciente {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private int gravedad;

    public Paciente(String nombre, String apellido, String dni, int gravedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.gravedad = gravedad;
    }

    public Paciente(int id, String nombre, String apellido, String dni, int gravedad){
        this(nombre, apellido, dni, gravedad);
        this.id = id;
    }

    @Override
    public String toString(){
        String result = "● ID: " + id + " " + nombre + " " + apellido + " " + dni + " Gravedad: " + gravedad;
        return result;
    }


    // SETTERS AND GETTERS ------------------------------------------------


    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getGravedad() {
        return gravedad;
    }

    public void setGravedad(int gravedad) {
        this.gravedad = gravedad;
    }

    public int getId() {
        return id;
    }
}

