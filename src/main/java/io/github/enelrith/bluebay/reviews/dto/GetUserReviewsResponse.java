package io.github.enelrith.bluebay.reviews.dto;

import java.time.Instant;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 */
public record GetUserReviewsResponse(Integer score, String description, Instant createdAt, Instant editedAt,
                                     String propertyAmaNumber) {
}