package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.bookings.exceptions.InvalidDatesException;
import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.exceptions.InvalidAmaNumberException;
import io.github.enelrith.bluebay.properties.exceptions.InvalidAreaException;
import io.github.enelrith.bluebay.properties.exceptions.InvalidPriceException;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetPropertyBySpecificationsRequest(String amaNumber,
                                                 Boolean isActive,
                                                 LocalDateTime checkIn,
                                                 LocalDateTime checkOut,
                                                 PropertyType propertyType,
                                                 @Positive(message = "Area cannot be negative")
                                                 BigDecimal minSquareMeters,
                                                 @Positive(message = "Area cannot be negative")
                                                 BigDecimal maxSquareMeters,
                                                 @Positive(message = "Price cannot be negative")
                                                 BigDecimal minPrice,
                                                 @Positive(message = "Price cannot be negative")
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
