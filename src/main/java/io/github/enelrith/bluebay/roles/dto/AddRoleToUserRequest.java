package io.github.enelrith.bluebay.roles.dto;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.data.RoleData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link io.github.enelrith.bluebay.roles.entities.Role}
 * Adds an existing role to a user
 * @param name Enum that determines the role that will be added to the user {@link io.github.enelrith.bluebay.enums.RoleNames}
 */
@Schema(description = "Request body for adding a new role in the database")
public record AddRoleToUserRequest(@NotNull RoleNames name) implements RoleData {
}
