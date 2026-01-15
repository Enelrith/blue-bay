package io.github.enelrith.bluebay.roles.dto.data;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.swagger.v3.oas.annotations.media.Schema;

public interface RoleData {
    @Schema(description = "The name of the new role", implementation = RoleNames.class, example = "USER")
    RoleNames name();

}
