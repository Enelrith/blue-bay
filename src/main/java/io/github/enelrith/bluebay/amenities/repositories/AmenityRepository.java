package io.github.enelrith.bluebay.amenities.repositories;

import io.github.enelrith.bluebay.amenities.entities.Amenity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    boolean existsByName(String name);
}