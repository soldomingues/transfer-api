package br.com.solangedomingues.transferapi.exception;

public class NegativeTransferValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NegativeTransferValueException(String message) {
        super(message);
    }
}
