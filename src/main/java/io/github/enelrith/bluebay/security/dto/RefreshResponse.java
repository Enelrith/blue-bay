package io.github.enelrith.bluebay.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO that contains the response of the /auth/refresh api
 * in {@link io.github.enelrith.bluebay.security.controllers.AuthController}
 * @param accessToken the user's new access token
 * @param type the token type
 */
@Schema(description = "Response returned whenever a user successfully refreshes their access token")
public record RefreshResponse(@Schema(description = "The new access token", example = "accessToken")
                              String accessToken,
                              @Schema(example = "Bearer")
                              String type) {
}
