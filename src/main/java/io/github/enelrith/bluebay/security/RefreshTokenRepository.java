package io.github.enelrith.bluebay.security;

import io.github.enelrith.bluebay.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link io.github.enelrith.bluebay.security.entities.RefreshToken}
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}