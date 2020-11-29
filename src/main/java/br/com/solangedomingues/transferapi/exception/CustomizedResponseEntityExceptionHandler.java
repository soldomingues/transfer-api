package br.com.solangedomingues.transferapi.exception;

import br.com.solangedomingues.transferapi.vo.ResponseCustomer;
import br.com.solangedomingues.transferapi.vo.Situation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseCustomer> handleUserNotFoundException(NotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeBalanceException.class)
    protected ResponseEntity<Object> handleNegativeBalanceException(NegativeBalanceException ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNumberAlreadyRegisteredException.class)
    protected ResponseEntity<Object> handleAccountNumberAlreadyRegisteredException(AccountNumberAlreadyRegisteredException ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceedecTransactionValueException.class)
    protected ResponseEntity<Object> handleAccountNumberAlreadyRegisteredException(ExceedecTransactionValueException ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeTransactionValueException.class)
    protected ResponseEntity<Object> handleNegativeTransactionValueException(NegativeTransactionValueException ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseCustomer(new Situation(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                new Date(), request.getDescription(false), null)), HttpStatus.BAD_REQUEST);
    }
}
