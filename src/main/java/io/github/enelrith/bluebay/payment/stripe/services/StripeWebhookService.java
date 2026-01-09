package io.github.enelrith.bluebay.payment.stripe.services;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import io.github.enelrith.bluebay.bookings.exceptions.BookingNotFoundException;
import io.github.enelrith.bluebay.bookings.repositories.BookingRepository;
import io.github.enelrith.bluebay.enums.BookingStatus;
import io.github.enelrith.bluebay.payment.IWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeWebhookService implements IWebhookService {
    private final BookingRepository bookingRepository;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    public void handleWebhook(String signature, String payload) throws SignatureVerificationException {
        try {
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);
            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

            switch (event.getType()) {
                case "checkout.session.completed" -> handleCheckout(stripeObject, BookingStatus.CONFIRMED);
                case "checkout.session.expired" -> handleCheckout(stripeObject,  BookingStatus.CANCELLED);
            }
        } catch (SignatureVerificationException e) {
            throw e;
        }
    }

    private void handleCheckout(StripeObject stripeObject, BookingStatus status) {
        var session = (Session) stripeObject;
        if (session != null) {
            var bookingId = session.getMetadata().get("bookingId");
            var booking = bookingRepository.findById(Long.valueOf(bookingId)).orElseThrow(() ->
                    new BookingNotFoundException("Booking not found"));
            if (booking.getStatus().equals(BookingStatus.CONFIRMED) && status.equals(BookingStatus.CANCELLED)) return;
            booking.setStatus(status);
            bookingRepository.save(booking);
        }
    }
}
