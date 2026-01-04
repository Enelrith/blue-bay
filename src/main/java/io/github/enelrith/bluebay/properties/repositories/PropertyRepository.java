package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.entities.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the properties table
 */
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    boolean existsByAmaNumber(String amaNumber);
}