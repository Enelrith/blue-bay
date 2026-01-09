package io.github.enelrith.bluebay.payment;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.payment.stripe.dto.PaymentGatewayResponse;

public interface IPaymentGateway {
    PaymentGatewayResponse createCheckoutSession(Booking booking) throws Exception;
}
