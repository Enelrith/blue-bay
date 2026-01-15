package io.github.enelrith.bluebay.security.controllers;

import io.github.enelrith.bluebay.security.dto.RefreshRequest;
import io.github.enelrith.bluebay.security.dto.RefreshResponse;
import io.github.enelrith.bluebay.security.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Handles all operations regarding user authentication")
public class AuthController {
    private final AuthService authService;

    /**
     * Uses a user's valid refresh token to send them a new JWT access token
     * @param request Contains a refresh token
     * @return The new access token
     */
    @Operation(
            summary = "Refreshes a user's JWT access token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token refreshed",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RefreshResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Refresh token not found",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody RefreshRequest request) {
        var refreshToken = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(new RefreshResponse(refreshToken, "Bearer"));
    }
}
