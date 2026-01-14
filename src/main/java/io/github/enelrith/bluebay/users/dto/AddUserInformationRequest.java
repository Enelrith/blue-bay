package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import io.github.enelrith.bluebay.users.exceptions.InvalidIdDocumentTypeException;
import io.github.enelrith.bluebay.users.exceptions.NotAnAdultException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

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
public record AddUserInformationRequest(@NotBlank(message = "First name cannot be blank")
                                        String firstName,
                                        @NotBlank(message = "Last name cannot be blank")
                                        String lastName,
                                        @NotNull(message = "Date of birth cannot be null")
                                        @Past(message = "The date of birth cannot be in the present or future")
                                        LocalDate dateOfBirth,
                                        @NotBlank(message = "Nationality cannot be blank")
                                        String nationality,
                                        @NotBlank(message = "The ID document type cannot be blank")
                                        String idDocumentType,
                                        @NotNull(message = "The id document number cannot be null")
                                        String idDocumentNumber) {
    public AddUserInformationRequest {
        LocalDate currentDate =  LocalDate.now();
        var age = Period.between(dateOfBirth, currentDate).getYears();
        if (age < 18) throw new NotAnAdultException("Must be 18 years old or older");

        if (!UserIdDocumentType.isValid(idDocumentType))
            throw new InvalidIdDocumentTypeException("This type of id document is not valid");
    }
}