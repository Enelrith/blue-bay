package io.github.enelrith.bluebay.amenities.mappers;

import io.github.enelrith.bluebay.amenities.dto.AddAmenityRequest;
import io.github.enelrith.bluebay.amenities.entities.Amenity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AmenityMapper {
    Amenity toEntity(AddAmenityRequest addAmenityRequest);

    AddAmenityRequest toAddAmenityRequest(Amenity amenity);
}