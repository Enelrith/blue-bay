package io.github.enelrith.bluebay.properties.mappers;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.entities.Property;
import org.mapstruct.*;
import org.springframework.http.ResponseEntity;

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
}