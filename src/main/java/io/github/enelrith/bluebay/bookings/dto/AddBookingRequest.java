package io.github.enelrith.bluebay.bookings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.enelrith.bluebay.enums.BookingPaymentType;
import io.github.enelrith.bluebay.enums.BookingSource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request body for creating a new booking")
public record AddBookingRequest(@NotNull(message = "Check in date cannot be null")
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                @Schema(
                                        description = "Customer's check in date (yyyy-MM-dd HH:mm)",
                                        example = "2026-03-12 11:15"
                                )
                                LocalDateTime checkIn,
                                @NotNull(message = "Check out date cannot be null")
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                @Schema(
                                        description = "Customer's check out date (yyyy-MM-dd HH:mm)",
                                        example = "2026-03-15 10:00"
                                )
                                LocalDateTime checkOut,
                                @NotNull(message = "Payment type cannot be null")
                                @Schema(
                                        description = "The way the customer will pay for their stay",
                                        examples = {
                                                "CASH",
                                                "CREDIT_CARD",
                                                "DEBIT_CARD"
                                        }
                                )
                                BookingPaymentType bookingPaymentType,
                                @NotNull(message = "Source cannot be null")
                                @Schema(
                                        description = "The source the customer used to create the booking",
                                        implementation =  BookingSource.class,
                                        examples = {
                                                "WALK_IN",
                                                "AIRBNB_WEBSITE",
                                                "BLUE_BAY_WEBSITE"
                                        }
                                )
                                BookingSource source) {
}
