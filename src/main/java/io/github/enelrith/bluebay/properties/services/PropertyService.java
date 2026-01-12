package io.github.enelrith.bluebay.properties.services;

import io.github.enelrith.bluebay.amenities.exceptions.AmenityAlreadyExistsException;
import io.github.enelrith.bluebay.amenities.exceptions.AmenityNotFoundException;
import io.github.enelrith.bluebay.amenities.repositories.AmenityRepository;
import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.properties.entities.PropertyAmenity;
import io.github.enelrith.bluebay.properties.entities.PropertyImage;
import io.github.enelrith.bluebay.properties.exceptions.ImageAlreadyExistsException;
import io.github.enelrith.bluebay.properties.exceptions.ImageNotFoundException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyAlreadyExistsException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.mappers.PropertyImageMapper;
import io.github.enelrith.bluebay.properties.mappers.PropertyMapper;
import io.github.enelrith.bluebay.properties.repositories.PropertyAmenityRepository;
import io.github.enelrith.bluebay.properties.repositories.PropertyImageRepository;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import io.github.enelrith.bluebay.properties.repositories.specifications.PropertySpec;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final AmenityRepository amenityRepository;
    private final PropertyAmenityRepository propertyAmenityRepository;
    private final PropertyImageMapper propertyImageMapper;
    private final PropertyImageRepository propertyImageRepository;

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

    @Transactional
    public AddPropertyAmenityResponse addAmenityToProduct(Integer propertyId, Integer amenityId, AddPropertyAmenityRequest request) {
        var amenity =  amenityRepository.findById(amenityId).orElseThrow(() ->
                new AmenityNotFoundException("This amenity does not exist"));
        var property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new PropertyNotFoundException("This property does not exist"));

        if (propertyAmenityRepository.existsByAmenityIdAndPropertyId(amenityId, propertyId))
            throw new AmenityAlreadyExistsException("This amenity already exists");

        PropertyAmenity propertyAmenity = new PropertyAmenity();
        propertyAmenity.setAmenity(amenity);
        propertyAmenity.setProperty(property);
        if (request.quantity() != null) propertyAmenity.setQuantity(request.quantity());

        propertyAmenityRepository.save(propertyAmenity);

        return propertyMapper.toAddPropertyAmenityResponse(propertyAmenity);
    }

    @Transactional
    public UpdatePropertyAmenityQuantityResponse updateAmenityQuantity(Integer id, UpdatePropertyAmenityQuantityRequest request) {
        var propertyAmenity = propertyAmenityRepository.findById(id).orElseThrow(() ->
                new AmenityNotFoundException("This property amenity doesn't exist"));
        propertyAmenity.setQuantity(request.quantity());
        propertyAmenityRepository.save(propertyAmenity);

        return propertyMapper.toUpdatePropertyAmenityQuantityResponse(propertyAmenity);
    }

    @Transactional
    public void deletePropertyAmenity(Integer id) {
        if (!propertyAmenityRepository.existsById(id)) throw new AmenityNotFoundException("This amenity does not exist");
        propertyAmenityRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String addPropertyImage(Integer id, AddPropertyImageRequest request, String baseUrl, String propertyImagesDirectory) {
        var property = propertyRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        if (propertyImageRepository.existsByNameAndProperty_Id(request.name(), id)) throw new ImageAlreadyExistsException("This image already exists for this property");

        Path imagePath = Paths.get(propertyImagesDirectory).resolve(request.name());
        if (!Files.exists(imagePath)) throw new ImageNotFoundException("This image does not exist");

       var propertyImage = propertyImageMapper.toEntity(request);
       propertyImage.setProperty(property);

       propertyImageRepository.save(propertyImage);

       return baseUrl + "/property-images/" + request.name();
    }
}
