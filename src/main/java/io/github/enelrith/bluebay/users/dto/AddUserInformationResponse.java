package io.github.enelrith.bluebay.users.dto;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;

import java.time.Instant;
import java.time.LocalDate;

public record AddUserInformationResponse(Long id,
                                         String firstName,
                                         String lastName,
                                         LocalDate dateOfBirth,
                                         String nationality,
                                         UserIdDocumentType idDocumentType,
                                         String idDocumentNumber,
                                         Instant completedAt) {
}
