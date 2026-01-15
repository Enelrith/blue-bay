package io.github.enelrith.bluebay.reviews.dto;

import io.github.enelrith.bluebay.reviews.dto.data.ReviewData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 * Used in {@link io.github.enelrith.bluebay.reviews.services.ReviewService}
 * Contains the user's  review score and description for a property
 * @param score The review score (1-5)
 * @param description The review's description
 */
@Schema(description = "Request body used when creating a new review")
public record AddReviewRequest(@NotNull(message = "The score cannot be null")
                               @Min(message = "The score must be at least 1", value = 1)
                               @Max(message = "The score cannot be more than 5", value = 5)
                               Integer score,
                               @Size(message = "The description must be between {min} and {max} characters long", min = 1, max = 255)
                               String description) implements ReviewData {
}