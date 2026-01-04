package io.github.enelrith.bluebay.properties.dto;

import io.github.enelrith.bluebay.enums.PropertyType;

import java.io.Serializable;
import java.math.BigDecimal;

public record AddPropertyResponse(String atakNumber,
                                  String amaNumber,
                                  BigDecimal squareMeters,
                                  String type,
                                  String street,
                                  String city,
                                  String postalCode,
                                  String country,
                                  boolean isActive,
                                  String region) implements Serializable {
}
