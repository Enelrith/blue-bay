package io.github.enelrith.bluebay.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController}
 * Used when the user is authenticated successfully
 * @param accessToken Generated access JWT for the user that was authenticated.
 *                    It will be sent as a header whenever a user makes a request to an endpoint that requires authorization
 * @param refreshToken Generated refresh JWT that will be stored in the database.
 *                     It will be used to generate a new access JWT token for the user whenever the old one expires
 * @param type The type of the token (Bearer)
 * @param email User email
 */
@Schema(description = "Response returned when a user is authenticated successfully")
public record LoginResponse(@Schema(description = "The user's JWT access token", example = "accessToken") String accessToken,
                            @Schema(description = "The user's JWT refresh token", example = "refreshToken") String refreshToken,
                            @Schema(description = "The type of the token. In this case it is 'Bearer'", example = "Bearer") String type,
                            @Schema(example = "user@gmail.com") String email) {
}
