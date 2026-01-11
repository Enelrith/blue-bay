package io.github.enelrith.bluebay.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test-ui")
    public String testUI() {
        return "test-ui";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

    @GetMapping("/properties-ui")
    public String properties() {
        return "properties";
    }

    @GetMapping("/bookings-ui")
    public String bookings() {
        return "bookings";
    }

    @GetMapping("/users-ui")
    public String users() {
        return "users";
    }

    @GetMapping("/amenities-ui")
    public String amenities() {
        return "amenities";
    }
}
