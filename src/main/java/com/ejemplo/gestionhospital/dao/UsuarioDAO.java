package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Usuario;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private final DataSource dataSource;

    public UsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Usuario obtenerUsuario(String username) {
        String query = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isAdmin");

                return new Usuario(username, password, isAdmin);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new AccessDataException("Error al consultar el usuario", e);
        }
    }
}
