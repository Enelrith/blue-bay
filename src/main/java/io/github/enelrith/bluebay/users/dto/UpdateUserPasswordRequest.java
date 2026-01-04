package io.github.enelrith.bluebay.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(@NotBlank(message = "Password cannot be blank")
                                        @Size(message = "Password must be between 6-72 characters long", min = 6, max = 72)
                                        String password) {
}
