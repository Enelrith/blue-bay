package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.dto.data.PropertyData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO for {@link io.github.enelrith.bluebay.properties.entities.Property}
 * Used in {@link io.github.enelrith.bluebay.properties.services.PropertyService}
 *
 * @param atakNumber ATAK number of the property
 * @param amaNumber AMA number of the property
 * @param squareMeters Area of the property in square meters
 * @param type Enum that determines the type of the property {@link io.github.enelrith.bluebay.enums.PropertyType}
 * @param street The street the property is located on
 * @param city The city the property is in
 * @param postalCode Postal code of the property
 * @param country Country the property is located in
 * @param isActive Current availability of the property
 * @param region Region the property is located in
 * @param longitude Longitude of the property on the map
 * @param latitude Latitude of the property on the map
 * @param nightlyRate The price the customer pays per night to stay in the property
 * @param cleaningFee The price of all the cleaning operations for the property
 */

@Schema(description = "Response that contains all information about a property")
public record GetPropertyResponse(@Schema(
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
                                          description = "The availability of the property",
                                          example = "true"
                                  )
                                  Boolean isActive,
                                  String region,
                                  BigDecimal longitude,
                                  BigDecimal latitude,
                                  BigDecimal nightlyRate,
                                  BigDecimal cleaningFee) implements PropertyData {
}
