package io.github.enelrith.bluebay.properties.services;

import io.github.enelrith.bluebay.properties.dto.AddPropertyRequest;
import io.github.enelrith.bluebay.properties.dto.AddPropertyResponse;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.properties.exceptions.PropertyAlreadyExistsException;
import io.github.enelrith.bluebay.properties.mappers.PropertyMapper;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public ResponseEntity<AddPropertyResponse> addProperty(AddPropertyRequest request) {
        if (propertyRepository.existsByAmaNumber(request.amaNumber())) throw new PropertyAlreadyExistsException(
                "This property already exists");

        var property = propertyMapper.toEntity(request);
        propertyRepository.save(property);

        var addPropertyResponseDto = propertyMapper.toAddPropertyResponse(property);

        return ResponseEntity.status(HttpStatus.CREATED).body(addPropertyResponseDto);
    }
}
