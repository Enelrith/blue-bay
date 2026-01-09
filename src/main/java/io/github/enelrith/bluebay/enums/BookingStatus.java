package io.github.enelrith.bluebay.enums;

/**
 * Enum for the status column in the bookings table
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CHECKED_IN,
    CHECKED_OUT,
    CANCELLED,
    REFUNDED,
    FAILED
}
