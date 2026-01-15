package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.dto.data.PropertyData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Response returned when a property is updated successfully")
public record UpdatePropertyResponse(@Schema(
                                             description = "ATAK number unique to the building the apartment is in",
                                             example = "12345676543"
                                     )
                                     String atakNumber,
                                     @Schema(
                                             description = "AMA number unique to an apartment or rental property",
                                             example = "13245316548"
                                     )
                                     String amaNumber,
                                     BigDecimal squareMeters,
                                     PropertyType type,
                                     String street,
                                     String city,
                                     String postalCode,
                                     String country,
                                     @Schema(
                                             examples = {
                                                     "true",
                                                     "false"
                                             }
                                     )
                                     Boolean isActive,
                                     String region,
                                     BigDecimal latitude,
                                     BigDecimal longitude,
                                     BigDecimal nightlyRate,
                                     BigDecimal cleaningFee) implements PropertyData {
}
