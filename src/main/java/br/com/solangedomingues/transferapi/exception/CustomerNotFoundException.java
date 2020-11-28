package br.com.solangedomingues.transferapi.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String exception) {
        super(exception);
    }
}
