package io.github.enelrith.bluebay.reviews.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 */
public record EditReviewRequest(
        @Min(message = "The score must be at least 1", value = 1) @Max(message = "The score cannot be more than 5", value = 5) Integer score,
        @Size(message = "The description can be 1-255 characters long", min = 1, max = 255) String description) implements Serializable {
}