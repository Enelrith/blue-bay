package io.github.enelrith.bluebay.exceptions;

import io.github.enelrith.bluebay.properties.exceptions.PropertyAlreadyExistsException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.security.exceptions.ForbiddenAccessException;
import io.github.enelrith.bluebay.users.exceptions.UserAlreadyExistsException;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Applies custom logic for different exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Custom logic for UserAlreadyExists exception
     * @param e exception that occurs
     * @return 409 Conflict if the email the user tries to register with already exists
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Custom logic for BadCredentials exception
     * @param e exception that occurs
     * @return 400 Bad Request if the email or password in the request is invalid
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password");
    }

    /**
     * Custom logic for exceptions triggered by invalid arguments in the request objects
     * This method extracts field-specific error messages from the exception and returns
     * them in a map, with the field names as keys and the error messages as values.
     *
     * @param e the MethodArgumentNotValid exception containing validation errors
     * @return 400 Bad Request along with the error names and messages
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<String> handleException(ForbiddenAccessException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(PropertyAlreadyExistsException.class)
    public ResponseEntity<String> handleException(PropertyAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<String> handleException(PropertyNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
