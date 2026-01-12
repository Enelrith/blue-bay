package io.github.enelrith.bluebay.reviews.mappers;

import io.github.enelrith.bluebay.reviews.dto.*;
import io.github.enelrith.bluebay.reviews.entities.Review;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    Review toEntity(AddReviewRequest addReviewRequest);

    Review toEntity(AddReviewResponse addReviewResponse);

    AddReviewResponse toAddReviewResponse(Review review);

    Review toEntity(EditReviewRequest editReviewRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review toEntity(EditReviewRequest editReviewRequest, @MappingTarget Review review);

    EditReviewResponse toEditReviewResponse(Review review);

    @Mapping(source = "property.amaNumber", target = "propertyAmaNumber")
    GetUserReviewsResponse toGetUserReviewResponse(Review review);

    List<GetUserReviewsResponse> toGetUserReviewsResponse(List<Review> review);

    @Mapping(source = "user.email", target = "userEmail")
    GetPropertyReviewsResponse toGetPropertyReviewResponse(Review review);

    List<GetPropertyReviewsResponse> toGetPropertyReviewsResponse(List<Review> review);
}