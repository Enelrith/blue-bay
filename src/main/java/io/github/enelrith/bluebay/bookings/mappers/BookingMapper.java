package io.github.enelrith.bluebay.bookings.mappers;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetUserBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetUserBookingResponse;
import io.github.enelrith.bluebay.bookings.entities.Booking;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    Booking toEntity(GetUserBookingRequest getUserBookingRequest);

    GetUserBookingRequest toDto(Booking booking);

    GetUserBookingResponse toGetUserBookingResponse(Booking booking);
    List<GetUserBookingResponse> toGetUserBookingsResponse(List<Booking> booking);

    Booking toEntity(AddBookingRequest addBookingRequest);
}