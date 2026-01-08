package io.github.enelrith.bluebay.bookings.services;

import io.github.enelrith.bluebay.bookings.mappers.BookingFeeMapper;
import io.github.enelrith.bluebay.bookings.repositories.BookingFeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingFeeService {
    private final BookingFeeRepository bookingFeeRepository;

    public BigDecimal getTotalClimateFeeAmount(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal squareMeters) {
        var daysBetween = ChronoUnit.DAYS.between(checkIn, checkOut);
        long normalDays = 0;
        long winterDays = 0;
        BigDecimal normalTotal =  BigDecimal.ZERO;
        BigDecimal winterTotal = BigDecimal.ZERO;
        var currentDateTime = checkIn;

        for (int i = 0; i < daysBetween; i++) {
            var currentMonth = currentDateTime.getMonth();

            if (currentMonth.getValue() >= 3 && currentMonth.getValue() <= 10) {
                normalDays += 1;
            } else {
                winterDays += 1;
            }

            currentDateTime = currentDateTime.plusDays(1);
        }

        if (normalDays > 0) {
            if (squareMeters.compareTo(BigDecimal.valueOf(80)) <= 0) {
                var normalAmount = bookingFeeRepository.findBookingFeeAmountDtoByType("climate_fee_normal_under_80sq");
                normalTotal = normalAmount.amount().multiply(BigDecimal.valueOf(normalDays));
            } else {
                //Not implemented properly yet
                var testBigNormalAmount = BigDecimal.valueOf(15);
                normalTotal = normalTotal.multiply(testBigNormalAmount);
            }
        }
        if (winterDays > 0) {
            if (squareMeters.compareTo(BigDecimal.valueOf(80)) <= 0) {
                var winterAmount = bookingFeeRepository.findBookingFeeAmountDtoByType("climate_fee_winter_under_80sq");
                winterTotal = winterAmount.amount().multiply(BigDecimal.valueOf(winterDays));
            } else {
                //Not implemented properly yet
                var testBigWinterAmount = BigDecimal.valueOf(4);
                winterTotal = winterTotal.multiply(testBigWinterAmount);
            }
        }
        return normalTotal.add(winterTotal);
    }

    public BigDecimal getTaxAmountByType(String type) {
        var amount = bookingFeeRepository.findBookingFeeAmountDtoByType(type);
        return amount.amount();
    }
}
