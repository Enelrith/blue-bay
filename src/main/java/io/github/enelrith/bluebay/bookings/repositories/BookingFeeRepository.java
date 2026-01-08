package io.github.enelrith.bluebay.bookings.repositories;

import io.github.enelrith.bluebay.bookings.dto.BookingFeeAmountDto;
import io.github.enelrith.bluebay.bookings.entities.BookingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingFeeRepository extends JpaRepository<BookingFee, Integer> {
    @Query("select new io.github.enelrith.bluebay.bookings.dto.BookingFeeAmountDto(bf.amount) from BookingFee bf where bf.type = :type")
    BookingFeeAmountDto findBookingFeeAmountDtoByType(@Param("type") String type);
}