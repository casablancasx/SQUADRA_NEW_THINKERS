package br.com.squadra.squadrajavabootcamp2024.infra;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<StandardError> resourceAlreadyException(ResourceAlreadyExistException exception){
        StandardError error = StandardError.builder()
                .mensagem(exception.getMessage())
                .status(404)
                .build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StandardError error = StandardError.builder()
                .mensagem(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .status(404)
                .build();

        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
