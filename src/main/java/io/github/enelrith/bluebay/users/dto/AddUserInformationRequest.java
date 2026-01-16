package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import io.github.enelrith.bluebay.users.exceptions.InvalidIdDocumentTypeException;
import io.github.enelrith.bluebay.users.exceptions.NotAnAdultException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.UserInformation}
 * Used for completing an existing users account and verifying it.
 * @param firstName First name of the user
 * @param lastName Last name of the user
 * @param dateOfBirth Date of birth of the user
 * @param nationality Nationality of the user
 * @param idDocumentType Enum used for determining the type of ID document the user wants to use {@link io.github.enelrith.bluebay.enums.UserIdDocumentType}
 * @param idDocumentNumber ID document number of the user
 */
@Schema(description = "Request object for adding user personal information to complete profile")
public record AddUserInformationRequest(@NotBlank(message = "First name cannot be blank")
                                        @Size(message = "First name must be between {min} and {max} characters long", min = 1, max = 100)
                                        @Schema(description = "User's first name", example = "John")
                                        String firstName,
                                        @NotBlank(message = "Last name cannot be blank")
                                        @Size(message = "Last name must be between {min} and {max} characters long", min = 1, max = 100)
                                        @Schema(description = "User's last name", example = "Snow")
                                        String lastName,
                                        @NotNull(message = "Date of birth cannot be null")
                                        @Past(message = "The date of birth cannot be in the present or future")
                                        @Schema(description = "User's date of birth. Must be 18+.", example = "1990-01-01")
                                        LocalDate dateOfBirth,
                                        @NotBlank(message = "Nationality cannot be blank")
                                        @Size(message = "Nationality must be between {min} and {max} characters long", min = 1, max = 100)
                                        @Schema(description = "User's nationality", example = "Greek")
                                        String nationality,
                                        @NotNull(message = "The ID document type cannot be null")
                                        @Schema(description = "Type of ID document provided", example = "PASSPORT")
                                        UserIdDocumentType idDocumentType,
                                        @NotNull(message = "The id document number cannot be null")
                                        @Size(message = "ID document number must be between {min} and {max} characters long", min = 1, max = 100)
                                        @Schema(description = "The unique number of the ID document", example = "AE123456")
                                        String idDocumentNumber) {
    public AddUserInformationRequest {
        LocalDate currentDate =  LocalDate.now();
        var age = Period.between(dateOfBirth, currentDate).getYears();
        if (age < 18) throw new NotAnAdultException("Must be 18 years old or older");

        if (!UserIdDocumentType.isValid(idDocumentType.name()))
            throw new InvalidIdDocumentTypeException("This type of id document is not valid");
    }
}