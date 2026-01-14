package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdatePropertyRequest(@Positive(message = "Area cannot be negative or zero")
                                    BigDecimal squareMeters,
                                    PropertyType type,
                                    @Size(message = "Street must be between {min} and {max} characters long", min = 1, max = 100)
                                    String street,
                                    @Size(message = "City must be between {min} and {max} characters long", min = 1, max = 100)
                                    String city,
                                    @Size(message = "Postal code must be between {min} and {max} characters long", min = 1, max = 20)
                                    String postalCode,
                                    @Size(message = "Country must be between {min} and {max} characters long", min = 1, max = 100)
                                    String country,
                                    @Size(message = "Region must be between {min} and {max} characters long", min = 1, max = 100)
                                    String region,
                                    BigDecimal latitude,
                                    BigDecimal longitude,
                                    @Positive(message = "Nightly rate must be a positive number")
                                    BigDecimal nightlyRate,
                                    @Positive(message = "Cleaning fee must be a positive number")
                                    BigDecimal cleaningFee)
{
}