package io.github.enelrith.bluebay.bookings.controllers;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetUserBookingResponse;
import io.github.enelrith.bluebay.bookings.dto.UpdateBookingStatusRequest;
import io.github.enelrith.bluebay.bookings.services.BookingService;
import io.github.enelrith.bluebay.payment.stripe.dto.PaymentGatewayResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<GetUserBookingResponse>>  getAllUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getAllUserBookings(userId));
    }

    @PostMapping("/{userId}/{propertyId}")
    public ResponseEntity<PaymentGatewayResponse>  addBooking(@PathVariable Long userId,
                                                              @PathVariable int propertyId,
                                                              @RequestBody @Valid AddBookingRequest request) {

        return ResponseEntity.ok(bookingService.addBooking(userId, propertyId, request));
    }

    /**
     * TODO: Add user authentication and response entity GetUserBookingResponse
     * @param id
     * @param request
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GetUserBookingResponse> updateBookingStatus(@PathVariable Long id,
                                                                      @Valid @RequestBody UpdateBookingStatusRequest request) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, request));
    }

    @PatchMapping("/{id}/cancellation")
    public ResponseEntity<GetUserBookingResponse> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
