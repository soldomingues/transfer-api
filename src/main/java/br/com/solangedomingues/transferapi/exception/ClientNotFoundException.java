package br.com.solangedomingues.transferapi.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String exception) {
        super(exception);
    }
}
