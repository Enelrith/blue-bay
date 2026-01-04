package io.github.enelrith.bluebay.users.entities;

import io.github.enelrith.bluebay.enums.UserIdDocumentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity representing an existing user's additional information.
 */
@Entity
@Table(name = "user_information")
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDateTime dateOfBirth;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_document_type", nullable = false, length = 50)
    private UserIdDocumentType userIdDocumentType;

    @Column(name = "id_document_number", nullable = false, length = 100)
    private String idDocumentNumber;

    @Column(name = "completed_at", nullable = true)
    private LocalDateTime completedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
