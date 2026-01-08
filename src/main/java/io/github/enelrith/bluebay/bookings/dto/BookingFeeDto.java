package io.github.enelrith.bluebay.bookings.dto;

import io.github.enelrith.bluebay.bookings.entities.BookingFee;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link BookingFee}
 */
public record BookingFeeDto(@Size(message = "Type cannot be more than {max} characters long", max = 50)
                            @NotBlank(message = "Type cannot be blank") 
                            String type,
                            @NotNull(message = "Amount cannot be null")
                            @Positive(message = "Amount must be positive")
                            @Digits(message = "Amount can can up to 10 {integer} digits and {fraction} decimals",
                                    integer = 10, fraction = 3)
                            BigDecimal amount,
                            @Size(message = "Description cannot be more than {max} characters long", max = 255)
                            @NotBlank(message = "Description cannot be blank") 
                            String description){
}