package io.github.enelrith.bluebay.properties.mappers;

import io.github.enelrith.bluebay.properties.dto.AddPropertyImageRequest;
import io.github.enelrith.bluebay.properties.dto.AddPropertyImageResponse;
import io.github.enelrith.bluebay.properties.entities.PropertyImage;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyImageMapper {
    PropertyImage toEntity(AddPropertyImageRequest addPropertyImageRequest);

    AddPropertyImageRequest toDto(PropertyImage propertyImage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyImage partialUpdate(AddPropertyImageRequest addPropertyImageRequest, @MappingTarget PropertyImage propertyImage);

    PropertyImage toEntity(AddPropertyImageResponse addPropertyImageResponse);

    AddPropertyImageResponse toDto1(PropertyImage propertyImage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyImage partialUpdate(AddPropertyImageResponse addPropertyImageResponse, @MappingTarget PropertyImage propertyImage);
}