package io.github.enelrith.bluebay.bookings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.enelrith.bluebay.enums.BookingPaymentType;
import io.github.enelrith.bluebay.enums.BookingSource;
import io.github.enelrith.bluebay.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddBookingRequest(@NotNull(message = "Check in date cannot be null")
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
                                LocalDateTime checkIn,
                                @NotNull(message = "Check out date cannot be null")
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
                                LocalDateTime checkOut,
                                @NotNull(message = "Payment type cannot be null") BookingPaymentType bookingPaymentType,
                                @NotNull(message = "Source cannot be null") BookingSource source) {
}
