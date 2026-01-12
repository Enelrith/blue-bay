package io.github.enelrith.bluebay.reviews.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 */
public record EditReviewResponse(Integer score, String description, Instant createdAt, Instant editedAt) {
}