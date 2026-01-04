package io.github.enelrith.bluebay.users.mappers;

import io.github.enelrith.bluebay.users.dto.*;
import io.github.enelrith.bluebay.users.entities.User;
import org.mapstruct.*;

/**
 * Mapper for User DTOs in {@link io.github.enelrith.bluebay.users.dto}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(RegisterUserRequest registerUserRequest);

    RegisterUserRequest toRegisterRequest(User user);

    RegisterUserResponse toRegisterResponse(User user);

    GetUserResponse toGetUserResponse(User user);

    UpdateUserEmailResponse  toUpdateUserEmailResponse(User user);

    UpdateUserPasswordResponse toUpdateUserPasswordResponse(User user);
}