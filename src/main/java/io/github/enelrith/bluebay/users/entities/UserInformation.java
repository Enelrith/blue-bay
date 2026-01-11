package io.github.enelrith.bluebay.users.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entity representing an existing user's additional information.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_information")
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_document_type", nullable = false, length = 50)
    private UserIdDocumentType idDocumentType;

    @Column(name = "id_document_number", nullable = false, length = 100)
    private String idDocumentNumber;

    @Column(name = "completed_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSX", timezone = "UTC")
    private Instant completedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
