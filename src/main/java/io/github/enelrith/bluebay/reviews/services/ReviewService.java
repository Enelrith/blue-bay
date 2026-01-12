package io.github.enelrith.bluebay.reviews.services;

import io.github.enelrith.bluebay.properties.exceptions.PropertyNotFoundException;
import io.github.enelrith.bluebay.properties.repositories.PropertyRepository;
import io.github.enelrith.bluebay.reviews.dto.*;
import io.github.enelrith.bluebay.reviews.exceptions.ReviewAlreadyExistsException;
import io.github.enelrith.bluebay.reviews.exceptions.ReviewNotFoundException;
import io.github.enelrith.bluebay.reviews.mappers.ReviewMapper;
import io.github.enelrith.bluebay.reviews.repositories.ReviewRepository;
import io.github.enelrith.bluebay.security.utilities.SecurityUtil;
import io.github.enelrith.bluebay.users.exceptions.UserNotFoundException;
import io.github.enelrith.bluebay.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    @PreAuthorize("hasRole('COMPLETED_ACCOUNT') or hasRole('ADMIN')")
    public AddReviewResponse addReview(Integer propertyId, AddReviewRequest request) {
        var userId = SecurityUtil.getCurrentUserId();
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var property = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        if (reviewRepository.existsByProperty_IdAndUser_Id(propertyId, userId))
            throw new ReviewAlreadyExistsException("This user has already reviewed this property");

        var review = reviewMapper.toEntity(request);
        review.setUser(user);
        review.setProperty(property);
        reviewRepository.save(review);

        return reviewMapper.toAddReviewResponse(review);
    }

    @Transactional
    public EditReviewResponse editReview(Long reviewId, EditReviewRequest request) {
        var userId = SecurityUtil.getCurrentUserId();
        var review = reviewRepository.findByIdAndUser_Id(reviewId, userId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        reviewMapper.toEntity(request, review);
        review.setEditedAt(Instant.now());

        return reviewMapper.toEditReviewResponse(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        var userId = SecurityUtil.getCurrentUserId();
        var review = reviewRepository.findByIdAndUser_Id(reviewId, userId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public List<GetUserReviewsResponse> getUserReviews(Long userId) {
        var reviews = reviewRepository.findAllByUser_Id(userId);

        return reviewMapper.toGetUserReviewsResponse(reviews);
    }

    public List<GetPropertyReviewsResponse> getPropertyReviews(Integer propertyId) {
        var reviews = reviewRepository.findAllByProperty_Id(propertyId);

        return reviewMapper.toGetPropertyReviewsResponse(reviews);
    }
}
