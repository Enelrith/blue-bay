package io.github.enelrith.bluebay.properties.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AddPropertyAmenityRequest(@Min(message = "Quantity cannot be less than 1", value = 1)
                                        @Max(message = "Quantity cannot be more than 99", value = 99)
                                        Integer quantity) {
}
