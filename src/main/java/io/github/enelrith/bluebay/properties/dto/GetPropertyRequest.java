package io.github.enelrith.bluebay.properties.dto;

import jakarta.validation.constraints.NotBlank;

public record GetPropertyRequest(@NotBlank(message = "AMA number cannot be blank") String amaNumber) {
}
