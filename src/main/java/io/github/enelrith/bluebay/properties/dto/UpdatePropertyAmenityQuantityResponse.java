package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.amenities.entities.Amenity;
import io.github.enelrith.bluebay.properties.entities.Property;

public record UpdatePropertyAmenityQuantityResponse(Integer quantity, Integer propertyId, Integer amenityId) {
}
