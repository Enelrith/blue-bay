package io.github.enelrith.bluebay.properties.repositories.specifications;

import io.github.enelrith.bluebay.bookings.entities.Booking;
import io.github.enelrith.bluebay.enums.PropertyType;
import io.github.enelrith.bluebay.properties.entities.Property;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PropertySpec {
    public static Specification<Property> hasAmaNumber(String amaNumber) {
        return (root, query, cb) -> cb.like(root.get("amaNumber"), "%" + amaNumber + "%");
    }

    public static Specification<Property> isActive(Boolean isActive) {
        return (root, query, cb) -> cb.equal(root.get("isActive"), isActive);
    }

    public static Specification<Property> isAvailable(LocalDateTime desiredCheckIn, LocalDateTime desiredCheckOut) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Booking> bookingRoot = subquery.from(Booking.class);
            Predicate conflict = cb.and(
                    cb.lessThan(bookingRoot.get("checkIn"), desiredCheckOut),
                    cb.greaterThan(bookingRoot.get("checkOut"), desiredCheckIn)
            );
            subquery.select(bookingRoot.get("property").get("id")).where(conflict);
            return cb.not(root.get("id").in(subquery));
        };
    }

    public static Specification<Booking> checkOutDate(LocalDateTime checkOut) {
        return (root, query, cb) -> cb.equal(root.get("checkOut"), checkOut);
    }

    public static Specification<Property> hasPropertyType(PropertyType propertyType) {
        return (root, query, cb) -> cb.equal(root.get("propertyType"), propertyType);
    }

    public static Specification<Property> hasMinArea(BigDecimal minSquareMeters) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("squareMeters"), minSquareMeters);
    }

    public static Specification<Property> hasMaxArea(BigDecimal maxSquareMeters) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("squareMeters"), maxSquareMeters);
    }

    public static Specification<Property> hasMinPrice(BigDecimal minPrice) {
        return (root, query, cb) -> cb.greaterThan(root.get("nightlyRate"), minPrice);
    }

    public static Specification<Property> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, cb) -> cb.lessThan(root.get("nightlyRate"), maxPrice);
    }
}
