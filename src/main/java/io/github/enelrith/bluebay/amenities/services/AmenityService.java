package io.github.enelrith.bluebay.amenities.services;

import io.github.enelrith.bluebay.amenities.dto.AddAmenityRequest;
import io.github.enelrith.bluebay.amenities.exceptions.AmenityAlreadyExistsException;
import io.github.enelrith.bluebay.amenities.exceptions.AmenityNotFoundException;
import io.github.enelrith.bluebay.amenities.mappers.AmenityMapper;
import io.github.enelrith.bluebay.amenities.repositories.AmenityRepository;
import io.github.enelrith.bluebay.properties.entities.PropertyAmenity;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.repositories.PropertyAmenityRepository;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AmenityService {
    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Transactional
    public void addAmenity(AddAmenityRequest request) {
        if (amenityRepository.existsByName(request.name())) throw new AmenityAlreadyExistsException("This amenity already exists");

        amenityRepository.save(amenityMapper.toEntity(request));
    }
}
