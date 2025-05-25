package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.UsuarioDAO;
import com.ejemplo.gestionhospital.model.Usuario;

public class LoginService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String username, String password) {
        Usuario user = usuarioDAO.obtenerUsuario(username);

        if (user != null) {

            return user.getPassword().equals(password);
        }

        return false;
    }
}
