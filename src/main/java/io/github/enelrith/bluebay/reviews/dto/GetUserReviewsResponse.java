package io.github.enelrith.bluebay.reviews.dto;

import io.github.enelrith.bluebay.reviews.dto.data.ReviewData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 * Contains the data of all the reviews a specific user made
 */
@Schema(description = "Response returned when a user has made at least one review about a property")
public record GetUserReviewsResponse(Integer score,
                                     String description,
                                     @Schema(description = "The date when the review was originally created (yyyy-MM-dd HH:mm)", example = "2026-02-16 14:02")
                                     Instant createdAt,
                                     @Schema(description = "The date when the review was edited (yyyy-MM-dd HH:mm", example = "2026-02-17 16:32")
                                     Instant editedAt,
                                     @Schema(description = "The unique AMA number of the property that has this review", example = "13245316548")
                                     String propertyAmaNumber) implements ReviewData {
}