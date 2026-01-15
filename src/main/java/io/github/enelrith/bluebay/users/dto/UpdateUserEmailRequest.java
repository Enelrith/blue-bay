package io.github.enelrith.bluebay.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for requesting an email address update.
 *
 * @param email The new email address
 */
@Schema(description = "Request body for updating the user's email address")
public record UpdateUserEmailRequest(@NotBlank(message = "Email cannot be blank")
                                     @Email(message = "Must be a valid email")
                                     @Size(message = "Email must be between {min} and {max} characters long", min = 1, max = 255)
                                     @Schema(description = "New email address", example = "usernew@example.com")
                                     String email) {
}
