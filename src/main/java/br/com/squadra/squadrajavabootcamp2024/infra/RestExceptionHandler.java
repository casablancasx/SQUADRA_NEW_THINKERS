package br.com.squadra.squadrajavabootcamp2024.infra;


import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException exception){
        StandardError error = StandardError.builder()
                .mensagem(exception.getMessage())
                .status(404)
                .build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<StandardError> resourceAlreadyException(ResourceAlreadyExistException exception){
        StandardError error = StandardError.builder()
                .mensagem(exception.getMessage())
                .status(409)
                .build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidantionError error = new ValidantionError();
        error.setNomeCampo(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField());
        error.setMensagem(ex.getBindingResult().getFieldError().getDefaultMessage());
        error.setStatus(400);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> invalidArgumentTypeException(MethodArgumentTypeMismatchException exception){
        StandardError error = StandardError.builder()
                .mensagem("O Tipo de dado informado é invalido para o campo " + exception.getName() +
                        " era esperado um valor do tipo " + exception.getRequiredType().getSimpleName() +
                        " e foi informado " + exception.getValue() + " de tipo " + exception.getValue().getClass().getSimpleName())
                .status(422)
                .build();
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
