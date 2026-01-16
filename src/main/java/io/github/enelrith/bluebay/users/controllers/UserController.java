package io.github.enelrith.bluebay.users.controllers;


import io.github.enelrith.bluebay.roles.dto.AddRoleToUserRequest;
import io.github.enelrith.bluebay.security.services.AuthService;
import io.github.enelrith.bluebay.security.dto.LoginRequest;
import io.github.enelrith.bluebay.security.dto.LoginResponse;
import io.github.enelrith.bluebay.users.dto.*;
import io.github.enelrith.bluebay.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles requests and responses in the /users endpoints
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users/")
@Tag(name = "Users", description = "Handles all of the user and user information operations")
public class UserController {
    private UserService userService;
    private AuthService authService;

    /**
     * Handles the user registration request and response
     * @param request contains the user registration credentials
     * @return 201 CREATED if the user registration is successful
     */
    @Operation(summary = "Register a new user in the database")
    @ApiResponse(
            responseCode = "201",
            description = "User created"
    )
    @ApiResponse(
            responseCode = "409",
            description = "User with that email already exists",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data in the request body",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    //TODO: Remove the register response.
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request){
        var user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Handles the user authentication request and response
     * @param request contains the user authentication credentials
     * @return 200 OK and the user's jwt access and refresh tokens, token type and email
     */
    @Operation(summary = "Handles the authentication of an existing user")
    @ApiResponse(
            responseCode = "200",
            description = "User authenticated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Wrong credentials or invalid data in the request body",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /**
     * Find a user by their unique id
     * @param id The user's id
     * @return 200 OK and a DTO with the user's email {@link io.github.enelrith.bluebay.users.dto.GetUserResponse}
     */
    @Operation(summary = "Find a user by using their unique id")
    @ApiResponse(
            responseCode = "200",
            description = "User found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetUserResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "User does not exist or invalid id format",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Deletes a user by their unique id
     * @param id The user's id
     * @return 204 NO CONTENT
     */
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deletes an existing user by their id")
    @ApiResponse(
            responseCode = "204",
            description = "User deleted"
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found or invalid id format",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a user's email by their id
     * @param id User's unique id
     * @param request Contains the updated email
     * @return 200 OK and the updated email {@link io.github.enelrith.bluebay.users.dto.UpdateUserEmailResponse}
     */
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update the email of an existing user")
    @ApiResponse(
            responseCode = "200",
            description = "Email updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateUserEmailResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found, invalid id format, or invalid email format",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @PutMapping("/{id}/email")
    public ResponseEntity<UpdateUserEmailResponse> updateUserEmail(@PathVariable Long id,
                                                                   @Valid @RequestBody UpdateUserEmailRequest request) {
        return ResponseEntity.ok(userService.updateUserEmailById(id, request.email()));
    }

    /**
     * Updates a user's password by their id
     * @param id User's unique id
     * @param request Contains the updated raw password
     * @return 200 OK
     */
    @Operation(summary = "Update the password of an existing user")
    @ApiResponse(
            responseCode = "200",
            description = "Password updated",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found, invalid id format, or invalid password format",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @PutMapping("/{id}/password")
    //TODO: Remove the updated password response
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UpdateUserPasswordResponse> updateUserPassword(@PathVariable Long id,
                                                                         @Valid @RequestBody UpdateUserPasswordRequest request) {
        return ResponseEntity.ok(userService.updateUserPasswordById(id, request.password()));
    }

    /**
     * Adds a role to an existing user
     * @param id The user's unique id
     * @param request Contains the role that will be added
     * @return 200 OK
     */
    @Operation(summary = "Adds a role to a user")
    @ApiResponse(
            responseCode = "200",
            description = "Role added"
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found, invalid role name or invalid id format",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "User already has this role",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}/roles")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long id, @Valid @RequestBody AddRoleToUserRequest request) {
        userService.addRoleToUser(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Links additional user information to an existing user
     * @param id User's id
     * @param request Contains the additional user information
     * @return 201 CREATED and the added information {@link io.github.enelrith.bluebay.users.dto.AddUserInformationResponse}
     */
    @Operation(summary = "Adds additional user information to a user")
    @ApiResponse(
            responseCode = "200",
            description = "User information added to user",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddUserInformationResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found or invalid data in user information request"
    )
    @ApiResponse(
            responseCode = "409",
            description = "User already has additional information linked to them"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{id}/user-information")
    public ResponseEntity<AddUserInformationResponse> addUserInformation(@PathVariable Long id,
                                                                         @Valid @RequestBody AddUserInformationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUserInformation(id, request));
    }

    /**
     * Updates the user information of a user
     * @param id The user's id
     * @param request The update user information
     * @return 200 OK and the updated user information {@link io.github.enelrith.bluebay.users.dto.AddUserInformationResponse}
     */
    @Operation(summary = "Updates the user information of a user")
    @ApiResponse(
            responseCode = "200",
            description = "User information updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddUserInformationResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "User not found, completed account role not found, or invalid data in the request",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}/user-information")
    public ResponseEntity<AddUserInformationResponse> updateUserInformation(@PathVariable Long id,
                                                                            @Valid @RequestBody UpdateUserInformationRequest request) {
        return ResponseEntity.ok(userService.updateUserInformation(id, request));
    }
}
