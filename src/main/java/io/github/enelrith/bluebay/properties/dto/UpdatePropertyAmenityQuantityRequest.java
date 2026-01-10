package io.github.enelrith.bluebay.properties.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdatePropertyAmenityQuantityRequest(@Min(message = "Quantity cannot be less than 1", value = 1)
                                                   @Max(message = "Quantity cannot be larget than 99", value = 99)
                                                   Integer quantity) {
}
