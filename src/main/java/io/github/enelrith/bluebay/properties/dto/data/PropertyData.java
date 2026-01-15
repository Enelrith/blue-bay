package io.github.enelrith.bluebay.properties.dto.data;

import io.github.enelrith.bluebay.enums.PropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * Interface for Property DTOs.
 * Used to centralize Swagger documentation for shared fields.
 */
public interface PropertyData {
    @Schema(
            description = "The area of the property in square meters",
            example = "75"
    )
    BigDecimal squareMeters();

    @Schema(
            description = "The type of the property",
            implementation = PropertyType.class,
            examples = {"STUDIO", "DOUBLE_BEDROOM"}
    )
    PropertyType type();

    @Schema(
            description = "The street the property is located on",
            example = "Street"
    )
    String street();

    @Schema(
            description = "The city the property is located in",
            example = "Athens"
    )
    String city();

    @Schema(
            description = "The postal code of the property",
            example = "12345"
    )
    String postalCode();

    @Schema(
            description = "The country the property is in",
            example = "Greece"
    )
    String country();

    @Schema(
            description = "The region the property is in",
            example = "Alimos"
    )
    String region();

    @Schema(
            description = "The latitude of the property on the map",
            example = "82.34523"
    )
    BigDecimal latitude();

    @Schema(
            description = "The longitude of the property on the map",
            example = "134.23487"
    )
    BigDecimal longitude();

    @Schema(
            description = "The nightly rate of the property",
            example = "70"
    )
    BigDecimal nightlyRate();

    @Schema(
            description = "The cleaning fee of the property",
            example = "15"
    )
    BigDecimal cleaningFee();
}