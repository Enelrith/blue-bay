package io.github.enelrith.bluebay.properties.mappers;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.properties.entities.PropertyAmenity;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for {@link io.github.enelrith.bluebay.properties.dto.AddPropertyRequest}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyMapper {
    Property toEntity(AddPropertyRequest request);

    AddPropertyRequest toAddPropertyRequest(Property property);

    AddPropertyResponse toAddPropertyResponse(Property property);

    GetPropertyResponse toGetPropertyResponse(Property property);

    List<GetPropertyResponse> toGetPropertyResponse(List<Property> properties);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePropertyFromRequest(UpdatePropertyRequest request, @MappingTarget Property property);

    Property toEntity(UpdatePropertyRequest request);

    UpdatePropertyResponse  toUpdatePropertyResponse(Property property);

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "amenity.id", target = "amenityId")
    AddPropertyAmenityResponse toAddPropertyAmenityResponse(PropertyAmenity propertyAmenity);

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "amenity.id", target = "amenityId")
    UpdatePropertyAmenityQuantityResponse toUpdatePropertyAmenityQuantityResponse(PropertyAmenity propertyAmenity);
}