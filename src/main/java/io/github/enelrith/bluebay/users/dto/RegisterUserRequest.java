package io.github.enelrith.bluebay.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.User}
 * Used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user registration request
 * @param email User desired email
 * @param password User desired password
 */
public record RegisterUserRequest(@Email(message = "Must be a valid email") @NotBlank(message = "Email cannot be blank") String email,
                                  @Size(message = "Password must be between 6-72 characters", min = 6, max = 72) @NotBlank(message = "Password cannot be blank") String password) implements Serializable {
}