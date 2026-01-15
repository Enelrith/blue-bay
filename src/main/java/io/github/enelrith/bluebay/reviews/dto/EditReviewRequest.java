package io.github.enelrith.bluebay.reviews.dto;

import io.github.enelrith.bluebay.reviews.dto.data.ReviewData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 * Used in {@link io.github.enelrith.bluebay.reviews.services.ReviewService}
 * Contains the new data that will replace the old review data
 */
@Schema(description = "Request body containing the updated review data")
public record EditReviewRequest(
        @Min(message = "The score must be at least 1", value = 1)
        @Max(message = "The score cannot be more than 5", value = 5)
        Integer score,
        @Size(message = "The description must be between {min} and {max} characters long", min = 1, max = 255)
        String description) implements ReviewData {
}