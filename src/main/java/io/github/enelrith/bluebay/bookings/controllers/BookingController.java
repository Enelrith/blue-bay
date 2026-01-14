package io.github.enelrith.bluebay.bookings.controllers;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetUserBookingResponse;
import io.github.enelrith.bluebay.bookings.dto.UpdateBookingStatusRequest;
import io.github.enelrith.bluebay.bookings.services.BookingService;
import io.github.enelrith.bluebay.payment.stripe.dto.PaymentGatewayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
@Tag(name = "Bookings")
public class BookingController {
    private final BookingService bookingService;

    /**
     * Endpoint for getting all of a user's bookings
     * @param userId The user's id
     * @return 200 OK if the operation is successful
     */
    @Operation(summary = "Get all bookings that belong to a specific user")
    @ApiResponse(
            responseCode = "200",
            description = "Bookings retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                    schema = @Schema(implementation = GetUserBookingResponse.class))
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid user id",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetUserBookingResponse>>  getAllUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getAllUserBookings(userId));
    }

    /**
     * Endpoint for creating a booking for a specific user and handling the checkout
     * @param userId The user's id
     * @param propertyId The id of the property that is to be booked
     * @param request Information about the booking
     * @return 201 CREATED and the checkout url
     */
    @Operation(summary = "Create a booking for a specific user")
    @ApiResponse(
            responseCode = "201",
            description = "Booking created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaymentGatewayResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid user id, property id, or data in the request"
    )
    @PostMapping("/{userId}/{propertyId}")
    public ResponseEntity<PaymentGatewayResponse>  addBooking(@PathVariable Long userId,
                                                              @PathVariable int propertyId,
                                                              @RequestBody @Valid AddBookingRequest request) {

        return ResponseEntity.ok(bookingService.addBooking(userId, propertyId, request));
    }

    /**
     * TODO: Add user authentication and response entity GetUserBookingResponse
     * Endpoint for updating the status of a user's existing booking
     * @param id The booking id
     * @param request The booking's updated status
     * @return 200 OK and the updated booking
     */
    @Operation(summary = "Updates a specific booking")
    @ApiResponse(
            responseCode = "200",
            description = "Booking updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetUserBookingResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid booking id"
    )
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
