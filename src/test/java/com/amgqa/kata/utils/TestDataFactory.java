package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;

import java.time.LocalDate;
import java.util.UUID;


public class TestDataFactory {

    /**
     * Generates a valid Booking object with dynamic dates and unique email.
     * @param roomId the room to book
     * @return Booking object ready for POST request
     */
    public static Booking createValidBooking(int roomId) {
        LocalDate today = LocalDate.now();
        BookingDates dates = new BookingDates(
                today.plusDays(1).toString(),   // checkin
                today.plusDays(5).toString()    // checkout
        );

        return new Booking(
                roomId,
                "John",
                "Doe",
                true,
                "test" + UUID.randomUUID() + "@example.com",
                "12345678901",
                dates
        );
    }
}
