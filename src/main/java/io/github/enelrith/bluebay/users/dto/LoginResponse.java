package io.github.enelrith.bluebay.users.dto;

import java.io.Serializable;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user login response
 * @param token Generated JWT for the user that was authenticated
 * @param type Token type
 * @param email User email
 */
public record LoginResponse(String token, String type, String email) implements Serializable {
}
