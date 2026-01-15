package io.github.enelrith.bluebay.reviews.dto;

import io.github.enelrith.bluebay.reviews.dto.data.ReviewData;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 * Used in {@link io.github.enelrith.bluebay.reviews.services.ReviewService}
 * Contains the added review's information
 */
@Schema(description = "Response returned when a new review is added successfully")
public record AddReviewResponse(Integer score,
                                String description) implements ReviewData {
}