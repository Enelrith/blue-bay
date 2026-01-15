package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.dto.data.PropertyData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.Property}
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 *
 * @param atakNumber Unique identifier number given to each building
 * @param amaNumber Unique identifier number given to each property (e.x rental apartment)
 * @param type Enum that determines the type of the property {@link io.github.enelrith.bluebay.enums.PropertyType}
 */
@Schema(description = "Response returned when the new property is added successfully")
public record AddPropertyResponse(
                                  @Schema(
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
