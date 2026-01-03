package io.github.enelrith.bluebay.users.mappers;

import io.github.enelrith.bluebay.users.dto.SetUserInformationRequest;
import io.github.enelrith.bluebay.users.entities.UserInformation;
import org.mapstruct.*;

/**
 * Mapper for UserInformation DTOs in {@link io.github.enelrith.bluebay.users.dto}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInformationMapper {
    UserInformation toEntity(SetUserInformationRequest setUserInformationRequest);

    SetUserInformationRequest toDto(UserInformation userInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserInformation partialUpdate(SetUserInformationRequest setUserInformationRequest, @MappingTarget UserInformation userInformation);
}