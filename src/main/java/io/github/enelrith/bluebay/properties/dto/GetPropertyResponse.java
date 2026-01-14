package io.github.enelrith.bluebay.properties.dto;

import java.math.BigDecimal;

public record GetPropertyResponse(String atakNumber,
                                  String amaNumber,
                                  BigDecimal squareMeters,
                                  String type,
                                  String street,
                                  String city,
                                  String postalCode,
                                  String country,
                                  Boolean isActive,
                                  String region,
                                  BigDecimal longitude,
                                  BigDecimal latitude,
                                  BigDecimal nightlyRate,
                                  BigDecimal cleaningFee){
}
