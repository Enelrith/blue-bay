package io.github.enelrith.bluebay.properties.dto;

import java.math.BigDecimal;

public record UpdatePropertyResponse(String atakNumber,
                                     String amaNumber,
                                     BigDecimal squareMeters,
                                     String type,
                                     String street,
                                     String city,
                                     String postalCode,
                                     String country,
                                     Boolean isActive,
                                     String region,
                                     BigDecimal latitude,
                                     BigDecimal longitude,
                                     BigDecimal nightlyRate,
                                     BigDecimal cleaningFee) {
}
