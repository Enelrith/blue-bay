package io.github.enelrith.bluebay.users.repositories;

import io.github.enelrith.bluebay.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link io.github.enelrith.bluebay.users.entities.User}
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}