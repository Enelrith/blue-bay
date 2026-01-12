package io.github.enelrith.bluebay.bookings.repositories;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository for the bookings table
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByUserIdAndPropertyId(Long userId, Integer propertyId);

    Optional<List<Booking>> findByUserId(Long userId);



    boolean existsByUserIdAndPropertyIdAndCheckOutIsAfter(Long userId, Integer propertyId, LocalDateTime checkIn);

    boolean existsByPropertyIdAndStatusNotIn(Integer propertyId, List<BookingStatus> statuses);

    boolean existsByUserIdAndPropertyIdAndStatusNotIn(Long userId, Integer propertyId, List<BookingStatus> statuses);

    Set<Booking> findAllByProperty_Id(Integer propertyId);

    boolean existsByProperty_IdAndCheckInIsLessThanAndCheckOutIsGreaterThan(Integer propertyId, LocalDateTime checkOut, LocalDateTime checkIn);

    Optional<Booking> findByIdAndUser_Id(Long bookingId, Long userId);
}