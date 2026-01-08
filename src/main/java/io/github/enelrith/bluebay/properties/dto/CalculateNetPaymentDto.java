package io.github.enelrith.bluebay.properties.dto;

import java.math.BigDecimal;

public record CalculateNetPaymentDto(BigDecimal nightlyRate, BigDecimal cleaningFee) {
}
