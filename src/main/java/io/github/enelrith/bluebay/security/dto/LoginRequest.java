package io.github.enelrith.bluebay.security.dto;

/**
 * DTO used in {@link io.github.enelrith.bluebay.users.controllers.UserController} for the user login request
 * @param email User email
 * @param password User password
 */
public record LoginRequest(String email, String password) {
}
