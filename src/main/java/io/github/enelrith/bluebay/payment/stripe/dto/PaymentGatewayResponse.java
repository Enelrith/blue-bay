package io.github.enelrith.bluebay.payment.stripe.dto;

public record PaymentGatewayResponse(Long bookingId, String checkoutUrl) {
}
