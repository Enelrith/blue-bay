package io.github.enelrith.bluebay.users.exceptions;

public class UserInformationAlreadyExistsException extends RuntimeException {
    public UserInformationAlreadyExistsException(String message) {
        super(message);
    }
}
