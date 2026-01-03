package io.github.enelrith.bluebay.security.controllers;

import io.github.enelrith.bluebay.security.dto.RefreshRequest;
import io.github.enelrith.bluebay.security.dto.RefreshResponse;
import io.github.enelrith.bluebay.security.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody RefreshRequest request) {
        var refreshToken = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(new RefreshResponse(refreshToken, "Bearer"));
    }
}
