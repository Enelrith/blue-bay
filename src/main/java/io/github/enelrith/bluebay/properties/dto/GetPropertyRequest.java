package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.Property}
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * Used to search a property by its AMA number
 * @param amaNumber The property's AMA number
 */
@Schema(description = "Request body that contains the AMA number of the property")
public record GetPropertyRequest(@NotBlank(message = "AMA number cannot be blank")
                                 @Size(message = "AMA number must be between {min} and {max} characters long", min = 1, max = 255)
                                 @Schema(example = "13245316548")
                                 String amaNumber) {
}
