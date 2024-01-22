package my.app.orderserviceso16.controller;

import jakarta.validation.ValidationException;
import my.app.orderserviceso16.util.exception.FindException;
import my.app.orderserviceso16.util.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ResponseException> personExceptionHandler(ValidationException validationException){
        ResponseException responseException = new ResponseException(
                validationException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FindException.class)
    private ResponseEntity<ResponseException> personExceptionHandler(FindException findException){
        ResponseException responseException = new ResponseException(
                findException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }
}
