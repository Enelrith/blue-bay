package io.github.enelrith.bluebay.properties.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.PropertyImage}
 */
public record AddPropertyImageRequest(
        @Size(message = "Image name must be {min} and {max} characters long", min = 1, max = 255)
        @Pattern(message = "The image must be a .jpg, .jpeg or .png file", regexp = "(?i).+\\.(jpg|jpeg|png)$")
        @NotBlank(message = "Image name cannot be blank")
        String name){
}