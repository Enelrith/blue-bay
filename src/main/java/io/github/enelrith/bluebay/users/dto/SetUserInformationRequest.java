package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.UserInformation}
 * Used for completing an existing users account and verifying it.
 * Not yet implemented
 * @param firstName First name of the user
 * @param lastName Last name of the user
 * @param dateOfBirth Date of birth of the user
 * @param nationality Nationality of the user
 * @param userIdDocumentType Enum used for determining the type of ID document the user wants to use {@link io.github.enelrith.bluebay.enums.UserIdDocumentType}
 * @param idDocumentNumber ID document number of the user
 */
public record SetUserInformationRequest(@NotBlank(message = "First name cannot be blank") String firstName,
                                        @NotBlank(message = "Last name cannot be blank") String lastName,
                                        @NotNull(message = "Date of birth cannot be null") @Past(message = "Invalid date of birth") LocalDateTime dateOfBirth,
                                        @NotBlank(message = "Nationality cannot be blank") String nationality,
                                        @NotNull(message = "ID document type cannot be null") UserIdDocumentType userIdDocumentType,
                                        @NotBlank(message = "ID document number cannot be blank") String idDocumentNumber) {
}