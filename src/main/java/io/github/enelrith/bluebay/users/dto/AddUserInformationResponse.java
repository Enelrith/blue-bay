package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Response DTO containing the completed user information.
 *
 * @param id The database ID of the user information
 * @param firstName The user's first name
 * @param lastName The user's last name
 * @param dateOfBirth The user's date of birth
 * @param nationality The user's nationality
 * @param idDocumentType The type of ID document used
 * @param idDocumentNumber The ID document number
 * @param completedAt Timestamp when the profile was completed
 */
@Schema(description = "Response containing added user information details")
public record AddUserInformationResponse(@Schema(description = "User information id", example = "1")
                                         Long id,
                                         @Schema(description = "The first name of the user", example = "John")
                                         String firstName,
                                         @Schema(description = "The last name of the user", example = "Snow")
                                         String lastName,
                                         @Schema(description = "The user's date of birth", example = "1990-01-01")
                                         LocalDate dateOfBirth,
                                         @Schema(description = "The user's nationality", example = "Greek")
                                         String nationality,
                                         @Schema(
                                                 description = "The type of document the user identifies themselves with",
                                                 example = "PASSPORT"
                                         )
                                         UserIdDocumentType idDocumentType,
                                         @Schema(description = "The number of the user's id document", example = "AE123456")
                                         String idDocumentNumber,
                                         @Schema(description = "The date and time the user completes their profile on (yyyy-MM-dd HH:mm)", example = "2026-02-03 12:53")
                                         Instant completedAt) {
}
