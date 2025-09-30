package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataFactory {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static String randomDate30DaysLater() {
        return LocalDate.now().plusDays(30).format(formatter);
    }

    private static int randomValidRoomId() {
        return ThreadLocalRandom.current().nextInt(1, 10); // [1–9]
    }

    private static String randomPhone(int length) {
        return ThreadLocalRandom.current()
                .ints(length, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
    }

    // =========================
    // VALID booking generator
    // =========================
    public static Booking createBooking() {
        String checkin = randomDate30DaysLater();
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
        return createBookingWithFirstnameLength(2, checkin, checkout);
    }

    public static Booking createBookingWithLongFirstname(String checkin, String checkout) {
        return createBookingWithFirstnameLength(51, checkin, checkout);
    }

    public static Booking createBookingWithShortLastname(String checkin, String checkout) {
        return createBookingWithLastnameLength(2, checkin, checkout);
    }

    public static Booking createBookingWithLongLastname(String checkin, String checkout) {
        return createBookingWithLastnameLength(51, checkin, checkout);
    }

    public static Booking createBookingWithInvalidPhone(String checkin, String checkout) {
        return createBookingWithPhoneLength(5, checkin, checkout); // too short
    }

    public static Booking createBookingWithTooLongPhone(String checkin, String checkout) {
        return createBookingWithPhoneLength(25, checkin, checkout); // too long
    }

    // =========================
    // Boundary / edge generators
    // =========================
    public static Booking createBookingWithFirstnameLength(int length, String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "J".repeat(length),
                "Doe",
                true,
                "firstname" + length + "@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithLastnameLength(int length, String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "D".repeat(length),
                true,
                "lastname" + length + "@test.com",
                randomPhone(11),
                new BookingDates(checkin, checkout)
        );
    }

    public static Booking createBookingWithPhoneLength(int length, String checkin, String checkout) {
        return new Booking(
                randomValidRoomId(),
                "John",
                "Doe",
                true,
                "phone" + length + "@test.com",
                randomPhone(length),
                new BookingDates(checkin, checkout)
        );
    }
}





















