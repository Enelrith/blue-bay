package io.github.enelrith.bluebay.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.User}
 * Used in {@link io.github.enelrith.bluebay.users.controllers.UserController}
 * @param email The user's email
 */
@Schema(description = "Response containing the user's email")
public record GetUserResponse(@NotBlank(message = "Email cannot be blank")
                              @Size(message = "Email must be between {min} and {max} characters long", min = 1, max = 255)
                              @Schema(description = "The user's email address", example = "user@example.com")
                              String email) {
}
