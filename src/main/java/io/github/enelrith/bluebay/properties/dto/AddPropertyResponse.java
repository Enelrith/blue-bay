package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;

import java.math.BigDecimal;

public record AddPropertyResponse(String atakNumber,
                                  String amaNumber,
                                  BigDecimal squareMeters,
                                  PropertyType type,
                                  String street,
                                  String city,
                                  String postalCode,
                                  String country,
                                  Boolean isActive,
                                  String region){
}
