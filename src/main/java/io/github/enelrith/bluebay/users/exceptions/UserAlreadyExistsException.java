package io.github.enelrith.bluebay.users.exceptions;

/**
 * Used when a user tries to register by using an existing email.
 * Handled by {@link io.github.enelrith.bluebay.exceptions.GlobalExceptionHandler}
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * @param message Displayed when the exception occurs
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
