package com.ejemplo.gestionhospital.model;

public class Sesion
{
    private static Usuario usuarioActual;

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean esAdmin() {
        return usuarioActual != null && usuarioActual.isAdmin();
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
