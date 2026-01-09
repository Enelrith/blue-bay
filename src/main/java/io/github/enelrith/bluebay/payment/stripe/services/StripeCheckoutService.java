package io.github.enelrith.bluebay.payment.stripe.services;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.payment.IPaymentGateway;
import io.github.enelrith.bluebay.payment.stripe.dto.PaymentGatewayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Primary
public class StripeCheckoutService implements IPaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;


    public PaymentGatewayResponse createCheckoutSession(Booking booking) throws StripeException {

        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?bookingId=" + booking.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel?bookingId=" + booking.getId())
                    .putMetadata("bookingId", booking.getId().toString())
                    .setCustomerEmail(booking.getUser().getEmail())
                    .setExpiresAt((System.currentTimeMillis() / 1000) + (30 * 60));

            long paymentInCents = booking.getTotalPayment()
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_UP)
                    .longValue();

            var lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount(paymentInCents)
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName("Blue Bay Booking")
                                                    .setDescription("There is an additional fee (Climate Resilience Fee) of " + booking.getTotalClimateFee().setScale(2, RoundingMode.HALF_UP) + "."
                                                    + " euros that must be paid in person at the end of your stay")
                                                    .build()
                                    ).build()
                    ).build();
            builder.addLineItem(lineItem);

            var session = Session.create(builder.build());

            return new PaymentGatewayResponse(booking.getId(), session.getUrl());
        } catch (StripeException e) {
            throw e;
        }
    }
}
