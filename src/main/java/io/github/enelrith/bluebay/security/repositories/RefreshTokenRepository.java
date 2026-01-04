package io.github.enelrith.bluebay.security.repositories;

import io.github.enelrith.bluebay.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link io.github.enelrith.bluebay.security.entities.RefreshToken}
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}