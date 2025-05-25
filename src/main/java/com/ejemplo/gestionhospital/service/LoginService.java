package com.ejemplo.gestionhospital.service;

import com.ejemplo.gestionhospital.dao.ConexionDB;
import com.ejemplo.gestionhospital.dao.UsuarioDAO;
import com.ejemplo.gestionhospital.model.Sesion;
import com.ejemplo.gestionhospital.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    private final UsuarioDAO usuarioDAO;
    private boolean valid;

    public LoginService(){
        valid = false;
        usuarioDAO = new UsuarioDAO(ConexionDB.getDataSource());
    }

    public boolean autenticar(String username, String password) {

        Usuario user = usuarioDAO.obtenerUsuario(username);
        valid = false;

        if (user != null) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            valid = BCrypt.checkpw(password, hashed);
        }

        Sesion.setUsuarioActual(user);
        return valid;
    }


}
