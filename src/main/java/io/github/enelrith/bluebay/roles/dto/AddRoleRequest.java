package io.github.enelrith.bluebay.roles.dto;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.data.RoleData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link io.github.enelrith.bluebay.roles.entities.Role}
 * Contains the name of the role that is to be created
 * @param name Enum that determines the name of the role {@link io.github.enelrith.bluebay.enums.RoleNames}
 */
@Schema(description = "Request body that contains the new role's name")
public record AddRoleRequest(@NotNull(message = "Role name cannot be null") RoleNames name) implements RoleData {
}
