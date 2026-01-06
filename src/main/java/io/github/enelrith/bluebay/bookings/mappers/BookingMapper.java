package io.github.enelrith.bluebay.bookings.mappers;

import io.github.enelrith.bluebay.bookings.dto.AddBookingRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsRequest;
import io.github.enelrith.bluebay.bookings.dto.GetAllUserBookingsResponse;
import io.github.enelrith.bluebay.bookings.entities.Booking;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    Booking toEntity(GetAllUserBookingsRequest getAllUserBookingsRequest);

    GetAllUserBookingsRequest toDto(Booking booking);

    List<GetAllUserBookingsResponse> toGetAllUserBookingsResponse(List<Booking> booking);

    Booking toEntity(AddBookingRequest addBookingRequest);
}