package io.github.enelrith.bluebay.users.controllers;


import io.github.enelrith.bluebay.security.services.AuthService;
import io.github.enelrith.bluebay.security.dto.LoginRequest;
import io.github.enelrith.bluebay.security.dto.LoginResponse;
import io.github.enelrith.bluebay.users.dto.GetUserResponse;
import io.github.enelrith.bluebay.users.dto.RegisterUserRequest;
import io.github.enelrith.bluebay.users.dto.RegisterUserResponse;
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
@RequestMapping("/users/")
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
}
