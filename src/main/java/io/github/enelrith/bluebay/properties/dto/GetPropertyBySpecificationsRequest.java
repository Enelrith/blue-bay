package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.bookings.exceptions.InvalidDatesException;
import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.exceptions.InvalidAmaNumberException;
import io.github.enelrith.bluebay.properties.exceptions.InvalidAreaException;
import io.github.enelrith.bluebay.properties.exceptions.InvalidPriceException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * for handling property search using specifications
 */
@Schema(description = "Request body for getting a property using specifications")
public record GetPropertyBySpecificationsRequest(@Size(message = "AMA number must be between {min} and {max} characters long", min = 1, max = 255)
                                                 @Schema(
                                                         description = "AMA number unique to an apartment or rental property",
                                                         example = "13245316548"
                                                 )
                                                 String amaNumber,
                                                 @Schema(example = "true")
                                                 Boolean isActive,
                                                 @Schema(
                                                         example = "2026-03-12 11:15"
                                                 )
                                                 LocalDateTime checkIn,
                                                 @Schema(
                                                         example = "2026-03-15 10:00"
                                                 )
                                                 LocalDateTime checkOut,
                                                 @Schema(
                                                         implementation = PropertyType.class,
                                                         example = "STUDIO"
                                                 )
                                                 PropertyType propertyType,
                                                 @Positive(message = "Area cannot be negative")
                                                 @Schema(
                                                         description = "Minimum property area in square meters",
                                                         example = "30"
                                                 )
                                                 BigDecimal minSquareMeters,
                                                 @Positive(message = "Area cannot be negative")
                                                 @Schema(
                                                         description = "Maximum property area in square meters",
                                                         example = "80"
                                                 )
                                                 BigDecimal maxSquareMeters,
                                                 @Positive(message = "Price cannot be negative")
                                                 @Schema(
                                                         description = "Minimum property nightly rate",
                                                         example = "50"
                                                 )
                                                 BigDecimal minPrice,
                                                 @Positive(message = "Price cannot be negative")
                                                 @Schema(
                                                         description = "Maximum property nightly rate",
                                                         example = "100"
                                                 )
                                                 BigDecimal maxPrice) {
    public GetPropertyBySpecificationsRequest {
        if (amaNumber != null) {
            for (char c : amaNumber.toCharArray()) {
                if (!Character.isDigit(c)) throw new InvalidAmaNumberException("Ama number must contains only digits");
            }
        }

        if (checkIn != null && checkOut == null) throw new InvalidDatesException("Check out date is required");
        if (checkIn == null && checkOut != null) throw new InvalidDatesException("Check in date is required");
        if (checkIn != null) {
            if (checkIn.isAfter(checkOut)) throw new InvalidDatesException("Check in cannot be after check out date");
            if (checkIn.isEqual(checkOut)) throw new InvalidDatesException("Check out cannot be in the same day as check in");
        }

        if (minSquareMeters != null && maxSquareMeters != null) {
            if (minSquareMeters.compareTo(maxSquareMeters) > 0)
                throw new InvalidAreaException("The minimum area must be smaller than the maximum area");
        }

        if (minPrice != null && maxPrice != null) {
            if (minPrice.compareTo(maxPrice) > 0)
                throw new InvalidPriceException("The minimum price must be smaller than the maximum price");
        }
    }
}
