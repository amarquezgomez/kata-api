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
        String email = firstname.toLowerCase() + "." + lastname.toLowerCase() + UUID.randomUUID() + "@example.com";
        String phone = generateRandomPhone(11, 21);

        return new Booking(
                roomId,
                firstname,
                lastname,
                depositpaid,
                email,
                phone,
                dates
        );
    }

    /**
     * Negative test case: invalid email
     */
    public static Booking createBookingWithInvalidEmail(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setEmail("invalid-email");
        return booking;
    }

    /**
     * Negative test case: firstname too short (<3 chars) and too long (>18 chars)
     */
    public static Booking createBookingWithShortFirstname(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setFirstname("Mo"); // < 3 chars
        return booking;
    }

    public static Booking createBookingWithLongFirstname(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setFirstname("ABCDEFGHIJKLMNOPQRS"); // > 18 chars
        return booking;
    }

    /**
     * Negative test case: lastname too short (<3 chars) and too long (>30 chars)
     */

    public static Booking createBookingWithShortLastname(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setLastname("Po");  // < 3 chars
        return booking;
    }

    public static Booking createBookingWithLongLastname(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setLastname("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE");  // > 30 chars
        return booking;
    }

    /**
     * Negative test case: checkout before checkin
     */
    public static Booking createBookingWithInvalidDates(int roomId) {
        LocalDate today = LocalDate.now();

        BookingDates invalidDates = new BookingDates(
                today.plusDays(5).toString(),
                today.plusDays(1).toString()
        );

        Booking booking = createValidBooking(roomId);
        booking.setBookingdates(invalidDates);
        return booking;
    }

    /**
     * roomId invalid: negative
     */
    public static Booking createBookingWithInvalidRoomId() {
        return createValidBooking(-1);
    }

    /**
     * phone too short (<11 chars) and too long (>21 chars)
     */
    public static Booking createBookingWithShortPhone(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setPhone(generateRandomPhone(5, 5));    // too short
        return booking;
    }

    public static Booking createBookingWithLongPhone(int roomId) {
        Booking booking = createValidBooking(roomId);
        booking.setPhone(generateRandomPhone(25, 25));    // too long
        return booking;
    }

    /**
     * Utility Methods
     */

    private static String generateRandomPhone(int minLength, int maxLength) {
        int length = minLength + RANDOM.nextInt(maxLength - minLength + 1);
        StringBuilder sb = new StringBuilder();
        for (int i; i < length; i++) {
            sb.append(RANDOM.nextInt(10))
        }
        return sb.toString();
    }

}
