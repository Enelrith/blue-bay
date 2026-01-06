package io.github.enelrith.bluebay.roles.controllers;

import io.github.enelrith.bluebay.enums.RoleNames;
import io.github.enelrith.bluebay.roles.dto.AddRoleRequest;
import io.github.enelrith.bluebay.roles.dto.GetAllRolesResponse;
import io.github.enelrith.bluebay.roles.services.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<GetAllRolesResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping
    public ResponseEntity<Void> addRole(@Valid @RequestBody AddRoleRequest request) {
        roleService.addRole(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
