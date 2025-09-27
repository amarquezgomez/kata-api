package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;


public class TestDataFactory {

    private static final Faker faker = new Faker();
    private static final Random RANDOM = new Random();

    /**
     * Generates a valid Booking object with randomized data.
     * @param roomId the room to book
     * @return Booking object ready for POST request
     */
    public static Booking createValidBooking(int roomId) {
        LocalDate today = LocalDate.now();

        BookingDates dates = new BookingDates(
                today.plusDays(1).toString(),   // checkin
                today.plusDays(5).toString()    // checkout
        );

        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        boolean depositpaid = RANDOM.nextBoolean();
        String uniqueEmail = firstname.toLowerCase() + "." + lastname.toLowerCase() + UUID.randomUUID() + "@example.com";
        String phone = faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "");    // strip non-digits

        // Ensure phone length meets API constraint (11-21 chars)
        if (phone.length() < 11) {
            phone = String.format("%011d", RANDOM.nextLong() % 100000000000L);
        } else if (phone.length() > 21) {
            phone = phone.substring(0,21);
        }

        return new Booking(
                roomId,
                firstname,
                lastname,
                depositpaid,
                uniqueEmail,
                phone,
                dates
        );
    }

    /**
     * Negative test case: invalid email
     */
    public static Booking createBookingWithInvalidEmail(int roomId) {
        Booking booking = createValidBooking(roomId);
        return booking;
    }

    /**
     * Negative test case: firstname too short
     */
    public static Booking createBookingWithShortFirstname(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setFirstname("Mo"); // < 3 chars
        return booking;
    }

    /**
     * Negative test case: checkout before checkin
     */
    public static Booking createBookingWithInvalidDates(int roomId) {
        LocalDate today = LocalDate.now();

        BookingDates dates = new BookingDates(
                today.plusDays(5).toString(),
                today.plusDays(1).toString()
        );

        Booking booking = createValidBooking(roomId);
        booking.setBookingdates(dates);
        return booking;
    }
}
