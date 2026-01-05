package io.github.enelrith.bluebay.users.dto;


/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.User}
 * Used in {@link io.github.enelrith.bluebay.users.controllers.UserController}
 * @param email The user's email
 */
public record GetUserResponse(String email) {
}
