package io.github.enelrith.bluebay.security.dto;

/**
 * DTO that contains the response of the /auth/refresh api
 * in {@link io.github.enelrith.bluebay.security.controllers.AuthController}
 * @param accessToken the user's new access token
 * @param type the token type
 */
public record RefreshResponse(String accessToken, String type) {
}
