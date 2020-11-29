package br.com.solangedomingues.transferapi.exception;

public class ExceededTransferValueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExceededTransferValueException(String message) {
        super(message);
    }
}
