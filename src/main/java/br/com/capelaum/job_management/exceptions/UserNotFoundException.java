package br.com.capelaum.job_management.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não existe");
    }
}
