package io.github.enelrith.bluebay.bookings.dto;

import io.github.enelrith.bluebay.enums.BookingPaymentType;
import io.github.enelrith.bluebay.enums.BookingSource;
import io.github.enelrith.bluebay.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetUserBookingResponse(LocalDateTime checkIn,
                                         LocalDateTime checkOut,
                                         BookingStatus status,
                                         BigDecimal netPayment,
                                         BigDecimal totalPayment,
                                         BookingPaymentType bookingPaymentType,
                                         BookingSource source,
                                         BigDecimal taxes,
                                         BigDecimal totalClimateFee) {
}
