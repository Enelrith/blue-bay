package io.github.enelrith.bluebay.bookings.dto;

import jakarta.validation.constraints.NotNull;

public record GetAllUserBookingsRequest(@NotNull Long userId) {
}
