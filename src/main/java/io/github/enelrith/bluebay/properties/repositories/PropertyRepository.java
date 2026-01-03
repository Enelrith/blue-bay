package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the properties table
 */
public interface PropertyRepository extends JpaRepository<Property, Integer> {
}