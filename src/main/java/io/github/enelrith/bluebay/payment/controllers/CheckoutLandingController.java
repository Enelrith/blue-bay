package io.github.enelrith.bluebay.payment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutLandingController {

    @GetMapping("/checkout-success")
    public String success(@RequestParam("bookingId") Long bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "checkout-success";
    }
}
