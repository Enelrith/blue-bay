package io.github.enelrith.bluebay.reviews.controllers;

import io.github.enelrith.bluebay.reviews.dto.*;
import io.github.enelrith.bluebay.reviews.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@Tag(name =  "Reviews", description = "Handles all operations regarding user reviews")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Add a new review for a property", description = "Required Roles: COMPLETED_ACCOUNT")
    @ApiResponse(responseCode = "201", description = "Review created")
    @ApiResponse(
            responseCode = "400",
            description = "User not found property not found, or invalid data in request body"
    )
    @ApiResponse(responseCode = "409", description = "User has already reviewed this property")
    @PostMapping("/{propertyId}")
    public ResponseEntity<AddReviewResponse> addReview(@PathVariable Integer propertyId, @Valid @RequestBody AddReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(propertyId, request));
    }

    @Operation(summary = "Edit an existing review")
    @ApiResponse(responseCode = "200", description = "Review edited")
    @ApiResponse(responseCode = "400", description = "Review not found or invalid data in request body")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<EditReviewResponse> editReview(@PathVariable Long reviewId, @Valid @RequestBody EditReviewRequest request) {
        return ResponseEntity.ok(reviewService.editReview(reviewId, request));
    }

    @Operation(summary = "Delete a review")
    @ApiResponse(responseCode = "204", description = "Review deleted")
    @ApiResponse(responseCode = "400", description = "Review not found")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all of a user's reviews")
    @ApiResponse(responseCode = "200", description = "Search completed")
    @ApiResponse(responseCode = "400", description = "User not found or invalid path variable")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserReviewsResponse>> getUserReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }

    @Operation(summary = "Get all of a property's reviews")
    @ApiResponse(responseCode = "200", description = "Search completed")
    @ApiResponse(responseCode = "400", description = "Property not found or invalid path variable")
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<GetPropertyReviewsResponse>> getPropertyReviews(@PathVariable Integer propertyId) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId));
    }
}
