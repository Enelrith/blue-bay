package io.github.enelrith.bluebay.roles.controllers;

import io.github.enelrith.bluebay.roles.dto.AddRoleRequest;
import io.github.enelrith.bluebay.roles.dto.GetAllRolesResponse;
import io.github.enelrith.bluebay.roles.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Handles all operations regarding user roles")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Gets all of the existing roles")
    @ApiResponse(
            responseCode = "200",
            description = "Search complete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetAllRolesResponse.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<GetAllRolesResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @Operation(summary = "Create a new role")
    @ApiResponse(
            responseCode = "201",
            description = "Role created",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Role already exists",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @PostMapping
    public ResponseEntity<Void> addRole(@Valid @RequestBody AddRoleRequest request) {
        roleService.addRole(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
