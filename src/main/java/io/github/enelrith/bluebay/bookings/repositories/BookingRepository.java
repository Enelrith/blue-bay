package io.github.enelrith.bluebay.bookings.repositories;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the bookings table
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
}