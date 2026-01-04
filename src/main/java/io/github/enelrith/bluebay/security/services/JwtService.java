package io.github.enelrith.bluebay.security.services;

import io.github.enelrith.bluebay.users.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Handles JWT operations.
 * This class provides methods for generating, validating, and extracting information from JWTs.
 */
@Service
public class JwtService {
    /**
     * The secret key used for signing and verifying JWTs.
     * Loaded from the application properties.
     */
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    /**
     * The expiration time (in milliseconds) for the generated access JWTs.
     * Loaded from the application properties.
     */
    @Value("${spring.security.jwt.access-expiration-ms}")
    @Getter
    private long jwtAccessExpiration;

    /**
     * The expiration time (in milliseconds) for the generated refresh JWTs.
     * Loaded from the application properties.
     */
    @Value("${spring.security.jwt.refresh-expiration-ms}")
    @Getter
    private long jwtRefreshExpiration;

    public String generateAccessToken(User user, long jwtAccessExpiration) {
        return generateToken(user, jwtAccessExpiration);
    }

    public String generateRefreshToken(User user, long jwtRefreshExpiration) {
        return generateToken(user, jwtRefreshExpiration);
    }
    /**
     * Generates a JWT token
     *
     * @param user the user the token will be created for
     * @param jwtExpiration when the token expires
     * @return the generated JWT token
     */
    private String generateToken(User user, long jwtExpiration) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Retrieves the signing key used for signing and verifying JWTs.
     *
     * @return the signing key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts a specific claim from the token
     *
     * @param token the token
     * @param claimsResolver a function to resolve the claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token
     *
     * @param token the token
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the email of the user from the token
     *
     * @param token the token
     * @return the email contained in the token
     */
    public String extractUsername(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    /**
     * Extract the user's id as a claim
     * @param token the user's JWT
     * @return the user's id as an Integer
     */
    public Long extractId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }
    /**
     * Validates the token based on the user details.
     *
     * @param token the token that gets validated
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the given token is expired.
     *
     * @param token the token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token
     *
     * @param token the token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
