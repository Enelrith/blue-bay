package io.github.enelrith.bluebay.properties.services;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.properties.exceptions.PropertyAlreadyExistsException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.mappers.PropertyMapper;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    @Transactional
    public AddPropertyResponse addProperty(AddPropertyRequest request) {
        if (propertyRepository.existsByAmaNumber(request.amaNumber())) throw new PropertyAlreadyExistsException(
                "This property already exists");

        var property = propertyMapper.toEntity(request);
        propertyRepository.save(property);

        return propertyMapper.toAddPropertyResponse(property);
    }

    public GetPropertyResponse getProperty(GetPropertyRequest request) {
        var property = propertyRepository.findByAmaNumber(request.amaNumber()).orElseThrow(() ->
                                            new PropertyNotFoundException("Property not found"));

        return propertyMapper.toGetPropertyResponse(property);
    }

    public List<GetPropertyResponse> getAllProperties() {
        var properties = propertyRepository.findAll();
        if (properties.isEmpty()) throw new PropertyNotFoundException("No properties found");

        return propertyMapper.toGetPropertyResponse(properties);
    }

    @Transactional
    public void deleteProperty(String amaNumber) {
        if (!propertyRepository.existsByAmaNumber(amaNumber)) throw new PropertyNotFoundException("Property not found");

        propertyRepository.deleteByAmaNumber(amaNumber);
    }

    @Transactional
    public UpdatePropertyResponse updateProperty(String amaNumber, UpdatePropertyRequest request) {
        var property = propertyRepository.findByAmaNumber(amaNumber).orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        propertyMapper.updatePropertyFromRequest(request, property);

        return propertyMapper.toUpdatePropertyResponse(property);
    }
}
