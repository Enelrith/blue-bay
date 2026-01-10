package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.entities.PropertyAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, Integer> {
    boolean existsByAmenityIdAndPropertyId(Integer amenityId, Integer propertyId);
}