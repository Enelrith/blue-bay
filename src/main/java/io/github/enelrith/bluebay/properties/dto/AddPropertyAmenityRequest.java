package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * DTO used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * Specifies the quantity of the amenity that will be added to a property
 * @param quantity
 */

@Schema(description = "Request body that contains the quantity of the added property amenity")
public record AddPropertyAmenityRequest(@Min(message = "Quantity cannot be less than 1", value = 1)
                                        @Max(message = "Quantity cannot be more than 99", value = 99)
                                        @Schema(example = "1") Integer quantity) {
}
