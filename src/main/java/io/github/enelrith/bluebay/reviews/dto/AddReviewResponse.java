package io.github.enelrith.bluebay.reviews.dto;

/**
 * DTO for {@link io.github.enelrith.bluebay.reviews.entities.Review}
 */
public record AddReviewResponse(Integer score, String description){
}