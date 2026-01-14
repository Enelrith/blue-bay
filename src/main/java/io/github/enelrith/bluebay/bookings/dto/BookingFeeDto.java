package io.github.enelrith.bluebay.bookings.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO for {@link io.github.enelrith.bluebay.bookings.entities.BookingFee}
 * Used in {@link io.github.enelrith.bluebay.bookings.services.BookingFeeService}
 */
public record BookingFeeDto(@Size(message = "Type must be between {min} and {max} characters long", min = 1, max = 50)
                            @NotBlank(message = "Type cannot be blank") 
                            String type,
                            @NotNull(message = "Amount cannot be null")
                            @Positive(message = "Amount must be positive")
                            @Digits(message = "Amount can can up to {integer} digits and {fraction} decimals",
                                    integer = 10, fraction = 3)
                            BigDecimal amount,
                            @Size(message = "Description must be between {min} and {max} characters long", min = 1, max = 255)
                            @NotBlank(message = "Description cannot be blank")
                            String description){
}