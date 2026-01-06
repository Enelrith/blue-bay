package io.github.enelrith.bluebay.bookings.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.enelrith.bluebay.enums.BookingSource;
import io.github.enelrith.bluebay.enums.BookingStatus;
import io.github.enelrith.bluebay.enums.BookingPaymentType;
import io.github.enelrith.bluebay.properties.entities.Property;
import io.github.enelrith.bluebay.users.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Table "bookings" in the database
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "check_in", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private BookingStatus status;

    @Column(name = "net_payment", nullable = false, precision = 10, scale = 2)
    private BigDecimal netPayment;

    @Column(name = "total_payment", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false, length = 50)
    private BookingPaymentType bookingPaymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 100)
    private BookingSource source;
}
