package me.elordenador.proyectobackend.exceptions;

public class LoginInvalid extends RuntimeException {
    public LoginInvalid(String message) {
        super(message);
    }
}
