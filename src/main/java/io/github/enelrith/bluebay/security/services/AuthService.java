package io.github.enelrith.bluebay.security.services;

import io.github.enelrith.bluebay.security.RefreshTokenRepository;
import io.github.enelrith.bluebay.security.entities.RefreshToken;
import io.github.enelrith.bluebay.security.dto.LoginRequest;
import io.github.enelrith.bluebay.security.dto.LoginResponse;
import io.github.enelrith.bluebay.users.entities.User;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Handles the authentication of the user during login
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
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
            String jwtAccessToken = jwtService.generateAccessToken(user, jwtService.getJwtAccessExpiration());
            String jwtRefreshTokenString = jwtService.generateRefreshToken(user, jwtService.getJwtRefreshExpiration());

            var refreshToken = new RefreshToken();
            refreshToken.setToken(jwtRefreshTokenString);
            refreshToken.setCreatedAt(Instant.now());
            refreshToken.setExpiresAt(refreshToken.getCreatedAt().plusMillis(jwtService.getJwtRefreshExpiration()));
            refreshToken.setUser(user);

            refreshTokenRepository.save(refreshToken);

            return new LoginResponse(jwtAccessToken, jwtRefreshTokenString,"Bearer", user.getEmail());
        }

        return null;
    }
}
