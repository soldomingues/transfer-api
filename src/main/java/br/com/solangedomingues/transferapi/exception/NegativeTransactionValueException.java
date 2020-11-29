package br.com.solangedomingues.transferapi.exception;

public class NegativeTransactionValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NegativeTransactionValueException(String message) {
        super(message);
    }
}
