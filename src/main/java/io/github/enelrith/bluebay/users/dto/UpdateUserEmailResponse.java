package io.github.enelrith.bluebay.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO returned after a successful email update.
 *
 * @param email The updated email address
 */
@Schema(description = "Response object containing the updated email")
public record UpdateUserEmailResponse(@Schema(description = "The updated email address", example = "usernew@example.com")
                                      String email) {
}
