package com.ejemplo.gestionhospital.model;

public class Usuario {

    private String username;
    private String password;
    private boolean isAdmin;

    public Usuario(String username, String password, boolean isAdmin){
        setPassword(password);
        setUsername(username);
        setIsAdmin(isAdmin);
    }

    private void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin(){
        return isAdmin;
    }
}

