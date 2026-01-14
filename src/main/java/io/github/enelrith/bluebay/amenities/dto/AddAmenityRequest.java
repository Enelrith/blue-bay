package io.github.enelrith.bluebay.amenities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.amenities.entities.Amenity}
 */
public record AddAmenityRequest(
        @Size(message = "Name cannot be more than 100 characters long", max = 100) @NotBlank(message = "Name cannot be blank") String name){
}