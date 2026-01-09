package io.github.enelrith.bluebay.payment.stripe.controllers;

import io.github.enelrith.bluebay.payment.IWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class StripeCheckoutController {
    private final IWebhookService webhookService;


    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestHeader("Stripe-Signature") String signature, @RequestBody String payload) {
        try {
            webhookService.handleWebhook(signature, payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
