package io.github.enelrith.bluebay.payment.stripe.exceptions;

public class StripePaymentFailedException extends RuntimeException {
    public StripePaymentFailedException(String message) {
        super(message);
    }
}
