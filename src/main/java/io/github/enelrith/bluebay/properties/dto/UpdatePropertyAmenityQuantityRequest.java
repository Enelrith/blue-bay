package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * Contains the updated quantity for a property's existing amenity
 */
@Schema(description = "Request body for updating the quantity of an existing property amenity")
public record UpdatePropertyAmenityQuantityRequest(@Min(message = "Quantity cannot be less than 1", value = 1)
                                                   @Max(message = "Quantity cannot be larget than 99", value = 99)
                                                   @Schema(example = "1")
                                                   Integer quantity) {
}
