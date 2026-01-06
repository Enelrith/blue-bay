package io.github.enelrith.bluebay.bookings.controllers;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsResponse;
import io.github.enelrith.bluebay.bookings.services.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<GetAllUserBookingsResponse>>  getAllUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getAllUserBookings(userId));
    }

    @PostMapping("/{userId}/{propertyId}")
    public ResponseEntity<Void>  addBooking(@PathVariable Long userId,
                                            @PathVariable int propertyId,
                                            @RequestBody @Valid AddBookingRequest request) {

        bookingService.addBooking(userId, propertyId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
