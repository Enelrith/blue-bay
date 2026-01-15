package io.github.enelrith.bluebay.roles.dto;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.data.RoleData;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for {@link io.github.enelrith.bluebay.roles.entities.Role}
 * Contains information about an existing role
 * It iss returned as a list in {@link io.github.enelrith.bluebay.roles.services.RoleService}
 * @param name Enum that determines the name of the role {@link io.github.enelrith.bluebay.enums.RoleNames}
 */
@Schema(description = "Response returned when at least one role exists")
public record GetAllRolesResponse(RoleNames name) implements RoleData {
}
