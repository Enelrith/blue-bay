package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import io.github.enelrith.bluebay.users.exceptions.InvalidIdDocumentTypeException;
import io.github.enelrith.bluebay.users.exceptions.NotAnAdultException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.UserInformation}.
 * Handles requests to update specific fields of the user information.
 *
 * @param firstName        First name to update
 * @param lastName         Last name to update
 * @param dateOfBirth      Date of birth to update (must be 18+)
 * @param nationality      Nationality to update
 * @param idDocumentType   ID document type to update
 * @param idDocumentNumber ID document number to update
 */
@Schema(description = "Request body for updating user personal information")
public record UpdateUserInformationRequest(@Size(message = "First name must be between {min} and {max} characters long", min = 1, max = 100)
                                           @Schema(description = "Updated first name", example = "Ned")
                                           String firstName,
                                           @Size(message = "Last name must be between {min} and {max} characters long", min = 1, max = 100)
                                           @Schema(description = "Updated last name", example = "Stark")
                                           String lastName,
                                           @Past(message = "Date of birth cannot be set in the future or the present")
                                           @Schema(description = "Updated date of birth", example = "2000-02-16")
                                           LocalDate dateOfBirth,
                                           @Size(message = "Nationality must be between {min} and {max} characters long", min = 1, max = 100)
                                           @Schema(description = "Updated nationality", example = "French")
                                           String nationality,
                                           @Schema(description = "Updated ID document type", example = "NATIONAL_ID")
                                           UserIdDocumentType idDocumentType,
                                           @Size(message = "First name must be between {min} and {max} characters long", min = 1, max = 100)
                                           @Schema(description = "Updated ID document number", example = "1009932134522")
                                           String idDocumentNumber){
    public UpdateUserInformationRequest {
        if (dateOfBirth != null) {
            LocalDate currentDate =  LocalDate.now();
            var age = Period.between(dateOfBirth, currentDate).getYears();
            if (age < 18) throw new NotAnAdultException("Must be 18 years old or older");
        }
        if (idDocumentType != null && !UserIdDocumentType.isValid(idDocumentType.name()))
            throw new InvalidIdDocumentTypeException("This type of id document is not valid");
    }
}