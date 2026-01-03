package io.github.enelrith.bluebay.security.dto;

import java.io.Serializable;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user login response
 * @param accessToken Generated access JWT for the user that was authenticated
 * @param refreshToken Generated refresh JWT that will be stored in the database
 * @param type Token type
 * @param email User email
 */
public record LoginResponse(String accessToken, String refreshToken, String type, String email) implements Serializable {
}
