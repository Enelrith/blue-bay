package io.github.enelrith.bluebay.users.mappers;

import io.github.enelrith.bluebay.users.dto.AddUserInformationRequest;
import io.github.enelrith.bluebay.users.dto.AddUserInformationResponse;
import io.github.enelrith.bluebay.users.dto.UpdateUserInformationRequest;
import io.github.enelrith.bluebay.users.entities.UserInformation;
import org.mapstruct.*;

/**
 * Mapper for UserInformation DTOs in {@link io.github.enelrith.bluebay.users.dto}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInformationMapper {
    UserInformation toEntity(AddUserInformationRequest addUserInformationRequest);

    AddUserInformationRequest toDto(UserInformation userInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserInformation partialUpdate(AddUserInformationRequest setUserInformationRequest, @MappingTarget UserInformation userInformation);

    AddUserInformationResponse toAddUserInformationResponse(UserInformation userInformation);

    UserInformation toEntity(UpdateUserInformationRequest updateUserInformationRequest);

    UpdateUserInformationRequest toUpdateUserInformationRequest(UserInformation userInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserInformation toEntity(UpdateUserInformationRequest updateUserInformationRequest, @MappingTarget UserInformation userInformation);
}