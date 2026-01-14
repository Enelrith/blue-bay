package io.github.enelrith.bluebay.bookings.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request parameter for getting a user's booking")
public record GetUserBookingRequest(@NotNull
                                    @Schema(
                                            description = "The user's id",
                                            example = "1"
                                    )
                                    Long userId) {
}
