package io.github.enelrith.bluebay.payment;

import com.stripe.exception.SignatureVerificationException;

public interface IWebhookService {
    public void handleWebhook(String signature, String payload) throws Exception;
}
