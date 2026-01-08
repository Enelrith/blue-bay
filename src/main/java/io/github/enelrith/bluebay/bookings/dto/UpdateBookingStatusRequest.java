package io.github.enelrith.bluebay.bookings.dto;

import io.github.enelrith.bluebay.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateBookingStatusRequest(@NotNull(message = "Status cannot be null") BookingStatus status) {
}
