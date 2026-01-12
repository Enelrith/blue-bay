package io.github.enelrith.bluebay.bookings.services;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetUserBookingResponse;
import io.github.enelrith.bluebay.bookings.dto.UpdateBookingStatusRequest;
import io.github.enelrith.bluebay.bookings.exceptions.BookingAlreadyExistsException;
import io.github.enelrith.bluebay.bookings.exceptions.BookingNotFoundException;
import io.github.enelrith.bluebay.bookings.exceptions.InvalidDatesException;
import io.github.enelrith.bluebay.bookings.mappers.BookingMapper;
import io.github.enelrith.bluebay.bookings.repositories.BookingRepository;
import io.github.enelrith.bluebay.enums.BookingStatus;
import io.github.enelrith.bluebay.payment.IPaymentGateway;
import io.github.enelrith.bluebay.payment.stripe.dto.PaymentGatewayResponse;
import io.github.enelrith.bluebay.payment.stripe.exceptions.StripePaymentFailedException;
import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import io.github.enelrith.bluebay.security.utilities.SecurityUtil;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final BookingFeeService bookingFeeService;
    private final IPaymentGateway paymentGateway;

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public List<GetUserBookingResponse> getAllUserBookings(Long  userId) {
        var booking = bookingRepository.findByUserId(userId).orElseThrow(
                () -> new BookingNotFoundException("Booking not found")
        );

        return bookingMapper.toGetUserBookingsResponse(booking);
    }

    @Transactional
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public PaymentGatewayResponse addBooking(Long userId, Integer propertyId, AddBookingRequest request) {
        if (ChronoUnit.DAYS.between(request.checkIn(), request.checkOut()) < 2) throw new InvalidDatesException("The minimum stay is 2 days");
        if (bookingRepository.existsByProperty_IdAndCheckInIsLessThanAndCheckOutIsGreaterThan(propertyId, request.checkOut(), request.checkIn()))
            throw new BookingAlreadyExistsException("These dates are not currently available for booking");
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var property = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        var booking = bookingMapper.toEntity(request);

        var netPayment = calculateNetPayment(propertyId, request.checkIn(), request.checkOut());
        var totalTaxes = calculateTotalTaxes(netPayment);
        var totalClimateFee = bookingFeeService.getTotalClimateFeeAmount(request.checkIn(),
                request.checkOut(), property.getSquareMeters());
        var totalPayment = calculateTotalPayment(netPayment);

        booking.setUser(user);
        booking.setProperty(property);
        booking.setNetPayment(netPayment);
        booking.setTotalPayment(totalPayment);
        booking.setTaxes(totalTaxes);
        booking.setTotalClimateFee(totalClimateFee);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
        propertyRepository.save(property);

        PaymentGatewayResponse paymentResponse = null;
        try {
            paymentResponse = paymentGateway.createCheckoutSession(booking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StripePaymentFailedException("Error creating checkout session");
        }

        return paymentResponse;
    }

    /**
     * TODO: Add refund logic
     * @param bookingId
     * @return
     */
    @Transactional
    public GetUserBookingResponse cancelBooking(Long bookingId) {
        var userId = SecurityUtil.getCurrentUserId();
        var booking = bookingRepository.findByIdAndUser_Id(bookingId, userId).orElseThrow(() ->
                new BookingNotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);

        return bookingMapper.toGetUserBookingResponse(booking);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GetUserBookingResponse updateBookingStatus(Long id, UpdateBookingStatusRequest request) {
        var booking = bookingRepository.findById(id).orElseThrow(() ->
                new BookingNotFoundException("Booking not found"));
        booking.setStatus(request.status());

        return bookingMapper.toGetUserBookingResponse(booking);
    }

    private BigDecimal calculateNetPayment(Integer propertyId, LocalDateTime checkIn,
                                           LocalDateTime checkOut) {
        var propertyDto = propertyRepository.findCalculateNetPaymentDtoById(propertyId);
        var daysBetween = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal days = BigDecimal.valueOf(Math.max(daysBetween, 1));

        return (propertyDto.nightlyRate().multiply(days)).add(propertyDto.cleaningFee());
    }

    private BigDecimal calculateTotalTaxes(BigDecimal netPayment) {
        var vatTaxPercentage = bookingFeeService.getTaxAmountByType("vat_tax");
        var municipalityTaxPercentage = bookingFeeService.getTaxAmountByType("municipality_tax");

        var municipalityTax = netPayment.multiply(municipalityTaxPercentage);
        var vatTax = netPayment.multiply(vatTaxPercentage);

        return municipalityTax.add(vatTax);
    }

    private BigDecimal calculateTotalPayment(BigDecimal netPayment) {
        var taxSum = calculateTotalTaxes(netPayment);

        return netPayment.add(taxSum).setScale(2, RoundingMode.HALF_UP);
    }
}
