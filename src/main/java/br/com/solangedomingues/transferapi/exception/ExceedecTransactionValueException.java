package br.com.solangedomingues.transferapi.exception;

public class ExceedecTransactionValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExceedecTransactionValueException(String message) {
        super(message);
    }
}
