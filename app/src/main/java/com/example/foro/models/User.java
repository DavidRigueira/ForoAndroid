package com.example.foro.models;

public class User {

    private int idUsuario;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String emailUsuario;
    private String passwordUsuario;
    private int logedo = 0;

    public User() {
    }

    public User(int idUsuario, String nombreUsuario, String apellidosUsuario, String emailUsuario, String passwordUsuario, int logedo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.logedo = logedo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public int getLogeado() {
        return logedo;
    }

    public void setLogedo(int logedo) { this.logedo = logedo; }

    @Override
    public String toString() {
        return "User Nombre = " + nombreUsuario +", Apellidos = " + apellidosUsuario +", Email = " + emailUsuario;
    }
}
