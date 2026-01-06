package io.github.enelrith.bluebay.bookings.services;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsResponse;
import io.github.enelrith.bluebay.bookings.exceptions.BookingAlreadyExistsException;
import io.github.enelrith.bluebay.bookings.exceptions.BookingNotFoundException;
import io.github.enelrith.bluebay.bookings.mappers.BookingMapper;
import io.github.enelrith.bluebay.bookings.repositories.BookingRepository;
import io.github.enelrith.bluebay.enums.BookingStatus;
import io.github.enelrith.bluebay.properties.exceptions.PropertyIsNotActiveException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public List<GetAllUserBookingsResponse> getAllUserBookings(Long  userId) {
        var booking = bookingRepository.findByUserId(userId).orElseThrow(
                () -> new BookingNotFoundException("Booking not found")
        );

        return bookingMapper.toGetAllUserBookingsResponse(booking);
    }

    @Transactional
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public void addBooking(Long userId, int propertyId, AddBookingRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var property = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        isBookingPossible(userId, propertyId);

        var booking = bookingMapper.toEntity(request);

        booking.setUser(user);
        booking.setProperty(property);

        bookingRepository.save(booking);
    }

    private void isBookingPossible(Long userId, Integer propertyId) {
        if (propertyRepository.existsByIdAndIsActiveIs(propertyId,false)) {
            throw new PropertyIsNotActiveException("This property is not currently available");
        }

        if (bookingRepository.existsByUserIdAndPropertyIdAndStatusNotIn(userId, propertyId, List.of(
                BookingStatus.CANCELLED, BookingStatus.REFUNDED, BookingStatus.CHECKED_OUT))) {
            throw new BookingAlreadyExistsException("This property is already booked to this customer");
        }

        if (bookingRepository.existsByPropertyIdAndStatusNotIn(propertyId,List.of(
                BookingStatus.CANCELLED, BookingStatus.REFUNDED, BookingStatus.CHECKED_OUT))) {
            throw new BookingAlreadyExistsException("This property is already booked to another customer");
        }
    }
}
