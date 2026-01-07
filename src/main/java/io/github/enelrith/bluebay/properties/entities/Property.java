package io.github.enelrith.bluebay.properties.entities;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Table "properties" in the database
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "atak_number", nullable = false, length = 255)
    private String atakNumber;

    @Column(name = "ama_number", nullable = false, unique = true, length = 255)
    private String amaNumber;

    @Column(name = "square_meters", nullable = false, precision = 8, scale = 2)
    private BigDecimal squareMeters;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private PropertyType type;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "is_active", insertable = false, nullable = false)
    private Boolean isActive;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "latitude", nullable = false, precision = 8, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "nightly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal nightlyRate;

    @Column(name = "cleaning_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal cleaningFee;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    private Set<Booking> bookings;
}
