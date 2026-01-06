package io.github.enelrith.bluebay.roles.services;

import io.github.enelrith.bluebay.roles.dto.AddRoleRequest;
import io.github.enelrith.bluebay.roles.dto.GetAllRolesResponse;
import io.github.enelrith.bluebay.roles.exceptions.RoleAlreadyExistsException;
import io.github.enelrith.bluebay.roles.mappers.RoleMapper;
import io.github.enelrith.bluebay.roles.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<GetAllRolesResponse> getRoles() {
        var roles = roleRepository.findAll();
        return roleMapper.toGetAllRolesResponse(roles);
    }

    @Transactional
    public void addRole(AddRoleRequest request) {
        if (roleRepository.existsByName(request.name())) throw new RoleAlreadyExistsException("Role already exists");

        var role = roleMapper.toEntity(request.name());

        roleRepository.save(role);
    }
}
