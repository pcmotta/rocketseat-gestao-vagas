package br.com.attomtech.gestaovagas.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Usuário já existe");
    }
}
