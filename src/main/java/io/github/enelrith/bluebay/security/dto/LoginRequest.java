package io.github.enelrith.bluebay.security.dto;

import jakarta.validation.constraints.*;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user login request
 * @param email User email
 * @param password User password
 */
public record LoginRequest(@NotBlank(message = "Email cannot be blank")
                           @Size(message = "Email must be between {min} and {max} characters long", min = 1, max = 255)
                           @Email(message = "Must be a valid email")
                           String email,
                           @NotNull(message = "Password cannot be null")
                           @Size(message = "Password must be between {min} and {max} characters long", min = 6, max = 72)
                           String password) {
}
