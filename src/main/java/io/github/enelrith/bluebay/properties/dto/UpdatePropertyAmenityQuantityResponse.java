package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * for displaying the updated property amenity quantity, the property's id and the amenity's id
 */
@Schema(description = "Response returned when the quantity of a property amenity is updated")
public record UpdatePropertyAmenityQuantityResponse(@Schema(example = "1") Integer quantity,
                                                    @Schema(example = "2") Integer propertyId,
                                                    @Schema(example = "1") Integer amenityId) {
}
