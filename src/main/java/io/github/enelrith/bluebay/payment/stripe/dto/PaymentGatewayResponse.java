package io.github.enelrith.bluebay.payment.stripe.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Checkout url of a booking that a user wants to create")
public record PaymentGatewayResponse(Long bookingId, String checkoutUrl) {
}
