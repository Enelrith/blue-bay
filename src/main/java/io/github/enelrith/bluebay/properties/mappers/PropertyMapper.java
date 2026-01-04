package io.github.enelrith.bluebay.properties.mappers;

import io.github.enelrith.bluebay.properties.dto.AddPropertyRequest;
import io.github.enelrith.bluebay.properties.dto.AddPropertyResponse;
import io.github.enelrith.bluebay.properties.entities.Property;
import org.mapstruct.*;

/**
 * Mapper for {@link io.github.enelrith.bluebay.properties.dto.AddPropertyRequest}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyMapper {
    Property toEntity(AddPropertyRequest request);

    AddPropertyRequest toAddPropertyRequest(Property property);

    AddPropertyResponse toAddPropertyResponse(Property property);
}