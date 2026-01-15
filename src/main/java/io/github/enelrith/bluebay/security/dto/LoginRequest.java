package io.github.enelrith.bluebay.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController}
 * Contains the user information required for authentication
 * @param email User email
 * @param password User password
 */
@Schema(description = "Request body containing an email and a password that will be used for authentication")
public record LoginRequest(@NotBlank(message = "Email cannot be blank")
                           @Size(message = "Email must be between {min} and {max} characters long", min = 1, max = 255)
                           @Email(message = "Must be a valid email")
                           @Schema(example = "user@gmail.com")
                           String email,
                           @NotNull(message = "Password cannot be null")
                           @Size(message = "Password must be between {min} and {max} characters long", min = 6, max = 72)
                           @Schema(example = "123456")
                           String password) {
}
