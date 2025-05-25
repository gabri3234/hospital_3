package com.ejemplo.gestionhospital.model;

public class Habitacion {

    private int id;
    private String nombre;
    private int capacidad;


    public Habitacion(int capacidad){
        setCapacidad(capacidad);
    }

    public Habitacion(String nombre, int capacidad) {
        setCapacidad(capacidad);
        setNombre(nombre);
    }

    public Habitacion(int id, String nombre, int capacidad) {
        this(nombre, capacidad);
        setId(id);
    }

    public Habitacion(){}

    @Override
    public String toString(){
        return "● Habitacion " + id + " Capacidad: " + capacidad;
    }


    // SETTERS AND GETTERS --------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}
