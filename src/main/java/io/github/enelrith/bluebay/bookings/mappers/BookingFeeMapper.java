package io.github.enelrith.bluebay.bookings.mappers;

import io.github.enelrith.bluebay.bookings.dto.BookingFeeAmountDto;
import io.github.enelrith.bluebay.bookings.dto.BookingFeeDto;
import io.github.enelrith.bluebay.bookings.entities.BookingFee;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingFeeMapper {
    BookingFee toEntity(BookingFeeDto bookingFeeDto);

    BookingFeeDto toDto(BookingFee bookingFee);

    BookingFeeAmountDto toBookingFeeAmountDto(BookingFee bookingFee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookingFee partialUpdate(BookingFeeDto bookingFeeDto, @MappingTarget BookingFee bookingFee);
}