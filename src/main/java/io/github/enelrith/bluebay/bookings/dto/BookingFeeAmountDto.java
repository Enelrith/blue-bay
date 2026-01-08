package io.github.enelrith.bluebay.bookings.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BookingFeeAmountDto(@NotNull(message = "Amount cannot be null")
                                  @Positive(message = "Amount must be positive")
                                  @Digits(message = "Amount can have {integer} digits and {fraction} decimals",
                                          integer = 10, fraction = 3)
                                  BigDecimal amount) {
}
