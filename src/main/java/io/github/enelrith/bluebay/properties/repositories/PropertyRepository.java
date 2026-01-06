package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.entities.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for the properties table
 */
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    boolean existsByAmaNumber(String amaNumber);

    Optional<Property> findByAmaNumber(String amaNumber);

    void deleteByAmaNumber(String amaNumber);

    boolean existsByIdAndIsActiveIs(Integer id, Boolean isActive);
}