package io.github.enelrith.bluebay.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UpdateUserEmailRequest(@NotBlank(message = "Email cannot be blank")
                                     @Email(message = "Must be a valid email")
                                     @Size(message = "Email must be between {min} and {max} characters long", min = 1, max = 255)
                                     String email) {
}
