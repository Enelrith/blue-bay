package io.github.enelrith.bluebay.properties.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.PropertyImage}
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 * @param name The file name of the image
 */
@Schema(description = "Request body for linking an image to a property")
public record AddPropertyImageRequest(
        @Size(message = "Image name must be {min} and {max} characters long", min = 1, max = 255)
        @Pattern(message = "The image must be a .jpg, .jpeg or .png file", regexp = "(?i).+\\.(jpg|jpeg|png)$")
        @NotBlank(message = "Image name cannot be blank")
        @Schema(
                description = "The name of the image file. Pattern: <property id>-<image number>.<file extension>",
                example = "1-1.png")
        String name){
}