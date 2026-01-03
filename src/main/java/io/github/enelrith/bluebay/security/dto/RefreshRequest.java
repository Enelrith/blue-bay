package io.github.enelrith.bluebay.security.dto;

import java.io.Serializable;

/**
 * DTO that contains the request body in the auth/refresh api
 * in {@link io.github.enelrith.bluebay.security.controllers.AuthController}
 * @param refreshToken the user's refresh token
 */
public record RefreshRequest(String refreshToken) implements Serializable {
}
