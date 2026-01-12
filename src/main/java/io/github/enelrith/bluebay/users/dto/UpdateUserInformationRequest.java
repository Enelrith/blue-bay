package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import io.github.enelrith.bluebay.users.exceptions.InvalidIdDocumentTypeException;
import io.github.enelrith.bluebay.users.exceptions.NotAnAdultException;
import jakarta.validation.constraints.Past;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * DTO for {@link io.github.enelrith.bluebay.users.entities.UserInformation}
 */
public record UpdateUserInformationRequest(String firstName,
                                           String lastName,
                                           @Past(message = "Date of birth cannot be set in the future or the present")
                                           LocalDate dateOfBirth,
                                           String nationality,
                                           String idDocumentType,
                                           String idDocumentNumber){
    public UpdateUserInformationRequest {
        if (dateOfBirth != null) {
            LocalDate currentDate =  LocalDate.now();
            var age = Period.between(dateOfBirth, currentDate).getYears();
            if (age < 18) throw new NotAnAdultException("Must be 18 years old or older");
        }
        if (idDocumentType != null && !UserIdDocumentType.isValid(idDocumentType))
            throw new InvalidIdDocumentTypeException("This type of id document is not valid");
    }
}