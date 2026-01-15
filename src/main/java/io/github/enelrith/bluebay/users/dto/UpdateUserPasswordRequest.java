package io.github.enelrith.bluebay.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for requesting a password update.
 *
 * @param password The new password
 */
@Schema(description = "Request body for updating the user's password")
public record UpdateUserPasswordRequest(@NotBlank(message = "Password cannot be blank")
                                        @Size(message = "Password must be between 6-72 characters long", min = 6, max = 72)
                                        @Schema(description = "New password", example = "654321")
                                        String password) {
}
