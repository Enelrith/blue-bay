package io.github.enelrith.bluebay.properties.services;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.properties.exceptions.PropertyAlreadyExistsException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.mappers.PropertyMapper;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import io.github.enelrith.bluebay.properties.repositories.specifications.PropertySpec;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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

    public List<GetPropertyResponse> getPropertiesBySpecifications(GetPropertyBySpecificationsRequest request) {
        Specification<Property> spec = (root, query, cb) -> cb.conjunction();

        if (request.amaNumber() != null) spec = spec.and(PropertySpec.hasAmaNumber(request.amaNumber()));
        if (request.isActive() != null) spec = spec.and(PropertySpec.isActive(request.isActive()));
        if (request.propertyType() != null) spec =  spec.and(PropertySpec.hasPropertyType(request.propertyType()));
        if (request.minSquareMeters() != null) spec = spec.and(PropertySpec.hasMinArea(request.minSquareMeters()));
        if (request.maxSquareMeters() != null) spec = spec.and(PropertySpec.hasMaxArea(request.maxSquareMeters()));
        if (request.checkIn() != null && request.checkOut() != null) spec = spec.and(PropertySpec.isAvailable(request.checkIn(), request.checkOut()));
        if (request.minPrice() != null) spec = spec.and(PropertySpec.hasMinPrice(request.minPrice()));
        if (request.maxPrice() != null) spec  = spec.and(PropertySpec.hasMaxPrice(request.maxPrice()));

        return propertyMapper.toGetPropertyResponse(propertyRepository.findAll(spec));
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
