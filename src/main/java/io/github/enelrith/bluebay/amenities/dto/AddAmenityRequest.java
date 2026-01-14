package io.github.enelrith.bluebay.amenities.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.amenities.entities.Amenity}
 * Used in {@link io.github.enelrith.bluebay.amenities.services.AmenityService}
 */
@Schema(
        description = "Data required to create a new amenity"
)
public record AddAmenityRequest(
        @Schema(
                description = "The name of the amenity",
                examples = {"Wi-Fi", "Bed", "TV"},
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Size(message = "Name must be between {min} and {max} characters long", min = 1, max = 100)
        @NotBlank(message = "Name cannot be blank")
        String name){
}