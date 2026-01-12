package io.github.enelrith.bluebay.reviews.controllers;

import io.github.enelrith.bluebay.reviews.dto.*;
import io.github.enelrith.bluebay.reviews.services.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{propertyId}")
    public ResponseEntity<AddReviewResponse> addReview(@PathVariable Integer propertyId, @Valid @RequestBody AddReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(propertyId, request));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<EditReviewResponse> editReview(@PathVariable Long reviewId, @Valid @RequestBody EditReviewRequest request) {
        return ResponseEntity.ok(reviewService.editReview(reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserReviewsResponse>> getUserReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<GetPropertyReviewsResponse>> getPropertyReviews(@PathVariable Integer propertyId) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId));
    }
}
