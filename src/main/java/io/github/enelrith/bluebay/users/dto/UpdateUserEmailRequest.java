package io.github.enelrith.bluebay.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UpdateUserEmailRequest(@NotBlank(message = "Email cannot be blank")
                                     @Email(message = "Must be a valid email")
                                     String email) {
}
