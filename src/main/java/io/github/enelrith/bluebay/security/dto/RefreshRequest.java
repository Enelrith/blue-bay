package io.github.enelrith.bluebay.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO that contains the request body in the auth/refresh api
 * in {@link io.github.enelrith.bluebay.security.controllers.AuthController}
 * @param refreshToken the user's refresh token
 */
@Schema(description = "Request body that contains a user's refresh token")
public record RefreshRequest(@Schema(example = "refreshToken") String refreshToken) {
}
