package io.github.enelrith.bluebay.security.services;

import io.github.enelrith.bluebay.users.dto.LoginRequest;
import io.github.enelrith.bluebay.users.dto.LoginResponse;
import io.github.enelrith.bluebay.users.entities.User;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Handles the authentication of the user during login
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * Authenticates a user when the login endpoint is reached
     *
     * @param request the login request containing the user's email and password
     * @return returns the JWT token, token type, and user email if successful, null otherwise
     *
     */
    public LoginResponse authenticate(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = (User) auth.getPrincipal();

        if (user != null) {
            String jwtToken = jwtService.generateToken(user);
            return new LoginResponse(jwtToken, "Bearer", user.getEmail());
        }

        return null;
    }
}
