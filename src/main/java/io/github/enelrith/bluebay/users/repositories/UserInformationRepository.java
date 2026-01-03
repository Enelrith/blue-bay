package io.github.enelrith.bluebay.users.repositories;

import io.github.enelrith.bluebay.users.entities.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository for {@link io.github.enelrith.bluebay.users.entities.UserInformation}
 */
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
}