package io.github.enelrith.bluebay.reviews.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 */
public record GetPropertyReviewsResponse(Integer score, String description, Instant createdAt, Instant editedAt,
                                         String userEmail) implements Serializable {
}