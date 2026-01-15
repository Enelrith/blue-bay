package io.github.enelrith.bluebay.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.User}
 * Used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user registration request
 * @param email User desired email
 * @param password User desired password
 */
@Schema(description = "Request object for user registration")
public record RegisterUserRequest(@Email(message = "Must be a valid email") @NotBlank(message = "Email cannot be blank")
                                  @Schema(description = "User's email address", example = "user@example.com")
                                  String email,
                                  @Size(message = "Password must be between {min} and {max} characters long", min = 6, max = 72)
                                  @NotBlank(message = "Password cannot be blank")
                                  @Schema(description = "User's raw password", example = "123456")
                                  String password) {
}