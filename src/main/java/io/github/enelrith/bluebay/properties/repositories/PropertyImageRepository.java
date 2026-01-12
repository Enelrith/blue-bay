package io.github.enelrith.bluebay.properties.repositories;

import io.github.enelrith.bluebay.properties.entities.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Integer> {
    boolean existsByNameAndProperty_Id(String name, Integer propertyId);
}