package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * Contains the quantity of the amenity that was added, the amenity's id and the id of the property it got added to.
 * @param quantity
 * @param amenityId
 * @param propertyId
 */
@Schema(description = "Response returned when successfully adding an amenity to a property")
public record AddPropertyAmenityResponse(@Schema(example = "1") Integer quantity,
                                         @Schema(example = "1") Integer amenityId,
                                         @Schema(example = "2") Integer propertyId) {
}