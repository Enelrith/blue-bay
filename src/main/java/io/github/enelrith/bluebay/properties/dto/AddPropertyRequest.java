package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.Property}
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 *
 * @param atakNumber ATAK number of the property
 * @param amaNumber AMA number of the property
 * @param squareMeters Area of the property in square meters
 * @param type Enum that determines the type of the property {@link io.github.enelrith.bluebay.enums.PropertyType}
 * @param street The street the property is located on
 * @param city The city the property is in
 * @param postalCode Postal code of the property
 * @param country Country the property is located in
 * @param region Region the property is located in
 */
public record AddPropertyRequest(@NotBlank(message = "ATAK  number cannot be blank")
                                 @Size(message = "ATAK number cannot be more than 255 characters long", max = 255)
                                 String atakNumber,
                                 @NotBlank(message = "AMA number cannot be blank")
                                 @Size(message = "AMA number cannot be more than 255 characters long", max = 255)
                                 String amaNumber,
                                 @NotNull(message = "Area cannot be null")
                                 @Positive(message = "Area cannot be negative or zero")
                                 BigDecimal squareMeters,
                                 PropertyType type,
                                 @NotBlank(message = "Street cannot be blank")
                                 @Size(message = "Street cannot be more than 100 characters long", max = 100)
                                 String street,
                                 @NotBlank(message = "City cannot be blank")
                                 @Size(message = "City cannot be more than 100 characters long", max = 100)
                                 String city,
                                 @NotBlank(message = "Postal code cannot be blank")
                                 @Size(message = "Postal code cannot be more than 20 characters long", max = 20)
                                 String postalCode,
                                 @NotBlank(message = "Country cannot be blank")
                                 @Size(message = "Country cannot be more than 100 characters long", max = 100)
                                 String country,
                                 @NotBlank(message = "Region cannot be blank")
                                 @Size(message = "Region cannot be more than 100 characters long", max = 100)
                                 String region)
{
}
