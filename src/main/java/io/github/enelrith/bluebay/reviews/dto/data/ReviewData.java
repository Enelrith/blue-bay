package io.github.enelrith.bluebay.reviews.dto.data;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ReviewData {

    @Schema(description = "The review score (1-5)", example = "4")
    Integer score();

    @Schema(description = "The review's description", example = "This apartment has a very nice view")
    String description();
}
