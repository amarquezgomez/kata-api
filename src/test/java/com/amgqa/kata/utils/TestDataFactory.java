package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataFactory {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static String randomDateWithin90Days(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(formatter);
    }

    private static int randomValidRoomId() {
        return ThreadLocalRandom.current().nextInt(1, 10); // [1–9]
    }

    private static String randomPhone(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return sb.toString();
    }

    // =========================
    // VALID booking generator
    // =========================
    public static Booking createBooking() {
        String checkin = randomDateWithin90Days(30);
        String checkout = LocalDate.parse(checkin).plusDays(3).format(formatter);

        return new Booking(
                randomValidRoomId(),
                "John",
                "Doe",
                true,
                "john.doe@test.com",
                randomPhone(ThreadLocalRandom.current().nextInt(11, 22)), // valid length [11–21]
                new BookingDates(checkin, checkout)
        );
    }

    // =========================
    // Negative booking generators
    // =========================
    public static Booking createBookingWithShortFirstname(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "J", // too short
                "Doe",
                true,
                "short@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithLongFirstname(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "J".repeat(51), // too long
                "Doe",
                true,
                "long@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithShortLastname(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "D", // too short
                true,
                "shortlastname@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithLongLastname(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "D".repeat(51), // too long
                true,
                "longlastname@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithInvalidPhone(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "Doe",
                true,
                "invalidphone@test.com",
                randomPhone(5), // too short (<11)
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithTooLongPhone(String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "Doe",
                true,
                "toolongphone@test.com",
                randomPhone(25), // too long (>21)
                new BookingDates(checkin, checkout)
        );
    }
}



















