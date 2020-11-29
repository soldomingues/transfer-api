package br.com.solangedomingues.transferapi.exception;

public class AccountNumberAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountNumberAlreadyRegisteredException(String message) {
        super(message);
    }
}
