package io.github.enelrith.bluebay.reviews.dto;

import io.github.enelrith.bluebay.reviews.dto.data.ReviewData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 * Contains all the reviews that belong to a specific property
 */
@Schema(description = "Response returned when at least one review is found for a property")
public record GetPropertyReviewsResponse(Integer score,
                                         String description,
                                         @Schema(description = "The date when the review was originally created (yyyy-MM-dd HH:mm)", example = "2026-02-16 14:02")
                                         Instant createdAt,
                                         @Schema(description = "The date when the review was edited (yyyy-MM-dd HH:mm", example = "2026-02-17 16:32")
                                         Instant editedAt,
                                         @Schema(description = "The email of the user that made the review", example = "user@example.com")
                                         String userEmail) implements ReviewData {
}