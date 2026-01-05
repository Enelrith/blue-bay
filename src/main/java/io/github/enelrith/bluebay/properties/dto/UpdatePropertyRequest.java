package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdatePropertyRequest(@Positive(message = "Area cannot be negative or zero")
                                    BigDecimal squareMeters,
                                    PropertyType type,
                                    @Size(message = "Street cannot be more than 100 characters long", max = 100)
                                    String street,
                                    @Size(message = "City cannot be more than 100 characters long", max = 100)
                                    String city,
                                    @Size(message = "Postal code cannot be more than 20 characters long", max = 20)
                                    String postalCode,
                                    @Size(message = "Country cannot be more than 100 characters long", max = 100)
                                    String country,
                                    @Size(message = "Region cannot be more than 100 characters long", max = 100)
                                    String region)
{
}