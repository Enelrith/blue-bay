package io.github.enelrith.bluebay.properties.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetPropertyRequest(@NotBlank(message = "AMA number cannot be blank")
                                 @Size(message = "AMA number must be between {min} and {max} characters long", min = 1, max = 255)
                                 String amaNumber) {
}
