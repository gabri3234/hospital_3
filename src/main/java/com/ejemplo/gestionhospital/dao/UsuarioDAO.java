package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario obtenerUsuario(String username) {
        String query = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {


                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isAdmin");

                Usuario user = new Usuario(username, password, isAdmin);
                return user;

            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al consultar el usuario", e);
        }
    }

}

