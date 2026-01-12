package io.github.enelrith.bluebay.reviews.repositories;

import io.github.enelrith.bluebay.reviews.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByProperty_IdAndUser_Id(Integer property_id, Long user_id);

    Optional<Review> findByIdAndUser_Id(Long reviewId, Long userId);

    List<Review> findAllByUser_Id(Long userId);

    List<Review> findAllByProperty_Id(Integer property_id);
}