package io.github.enelrith.bluebay.bookings.repositories;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for the bookings table
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByUserIdAndPropertyId(Long userId, Integer propertyId);

    Optional<List<Booking>> findByUserId(Long userId);


    boolean existsByUserIdAndPropertyIdAndCheckOutIsAfter(Long userId, Integer propertyId, LocalDateTime checkIn);

    boolean existsByPropertyIdAndStatusNotIn(Integer propertyId, List<BookingStatus> statuses);

    boolean existsByUserIdAndPropertyIdAndStatusNotIn(Long userId, Integer propertyId, List<BookingStatus> statuses);
}