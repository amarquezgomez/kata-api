package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FixedBookingFactory {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Create a fixed, valid booking (deterministic).
     */
    public static Booking createFixedValidBooking() {
        LocalDate checkin = LocalDate.now().plusDays(7);
        LocalDate checkout = checkin.plusDays(3);

        return new Booking(
                1,                                 // roomId
                "Alice",                           // firstname
                "Smith",                           // lastname
                true,                              // depositpaid (primitive boolean)
                "alice.smith@example.com",         // email
                "12345678901",                     // phone
                new BookingDates(
                        checkin.format(formatter),
                        checkout.format(formatter)
                )
        );
    }
}




