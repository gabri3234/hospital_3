package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.UsuarioDAO;
import com.ejemplo.gestionhospital.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private boolean valid;





    public boolean autenticar(String username, String password) {

        Usuario user = usuarioDAO.obtenerUsuario(username);
        valid = false;

        if (user != null) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            valid = BCrypt.checkpw(password, hashed);
        }

        return valid;
    }
}
