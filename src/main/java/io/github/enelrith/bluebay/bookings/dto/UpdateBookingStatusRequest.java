package io.github.enelrith.bluebay.bookings.dto;

import io.github.enelrith.bluebay.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for updating a booking's status")
public record UpdateBookingStatusRequest(@NotNull(message = "Status cannot be null")
                                         @Schema(
                                                 description = "The updated booking status",
                                                 implementation = BookingStatus.class,
                                                 examples = {
                                                         "COMPLETED",
                                                         "CANCELLED"
                                                 }
                                         )
                                         BookingStatus status) {
}
