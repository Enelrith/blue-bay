package io.github.enelrith.bluebay.users.controllers;


import io.github.enelrith.bluebay.roles.dto.AddRoleToUserRequest;
import io.github.enelrith.bluebay.security.services.AuthService;
import io.github.enelrith.bluebay.security.dto.LoginRequest;
import io.github.enelrith.bluebay.security.dto.LoginResponse;
import io.github.enelrith.bluebay.users.dto.*;
import io.github.enelrith.bluebay.users.services.UserService;
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
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private AuthService authService;

    /**
     * Handles the user registration request and response
     * @param request contains the user registration credentials
     * @return 201 CREATED if the user registration is successful
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request){
        var user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Handles the user authentication request and response
     * @param request contains the user authentication credentials
     * @return 200 OK if the user authentication is successful
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<UpdateUserEmailResponse> updateUserEmail(@PathVariable Long id,
                                                                   @Valid @RequestBody UpdateUserEmailRequest request) {
        return ResponseEntity.ok(userService.updateUserEmailById(id, request.email()));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UpdateUserPasswordResponse> updateUserPassword(@PathVariable Long id,
                                                                         @Valid @RequestBody UpdateUserPasswordRequest request) {
        return ResponseEntity.ok(userService.updateUserPasswordById(id, request.password()));
    }

    @PatchMapping("/{id}/roles")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long id, @Valid @RequestBody AddRoleToUserRequest request) {
        userService.addRoleToUser(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-information/{id}")
    public ResponseEntity<AddUserInformationResponse> addUserInformation(@PathVariable Long id,
                                                                         @Valid @RequestBody AddUserInformationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUserInformation(id, request));
    }
}
