package io.github.enelrith.bluebay.roles.dto;

import io.github.enelrith.bluebay.enums.RoleNames;
import jakarta.validation.constraints.NotNull;

public record AddRoleToUserRequest(@NotNull RoleNames name) {
}
