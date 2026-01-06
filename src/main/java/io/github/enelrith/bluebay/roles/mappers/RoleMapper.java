package io.github.enelrith.bluebay.roles.mappers;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.AddRoleRequest;
import io.github.enelrith.bluebay.roles.dto.GetAllRolesResponse;
import io.github.enelrith.bluebay.roles.entities.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    List<GetAllRolesResponse> toGetAllRolesResponse(List<Role> roles);

    Role toEntity(RoleNames name);
}