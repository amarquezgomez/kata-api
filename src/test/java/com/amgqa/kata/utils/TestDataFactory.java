package com.amgqa.kata.utils;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;
import net.datafaker.Faker;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataFactory {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** ---------------- Positive Booking ---------------- */
    public static Booking createBooking() {
        return createAvailableBooking(getDefaultCheckin(), getDefaultCheckout());
    }

    public static Booking createAvailableBooking(String checkin, String checkout) {
        int roomId = RoomFactory.createRoom();
        String firstname = randomFirstname();
        String lastname = randomLastname();
        String email = randomEmail(firstname, lastname);
        String phone = randomValidPhone();
        return new Booking(roomId, firstname, lastname, true, email, phone, new BookingDates(checkin, checkout));
    }

    /** ---------------- Field Generators ---------------- */
    public static String randomFirstname() {
        String name;
        do { name = faker.name().firstName().replaceAll("[^a-zA-Z]", ""); }
        while (name.length() < 3 || name.length() > 18);
        return name;
    }

    public static String randomLastname() {
        String name;
        do { name = faker.name().lastName().replaceAll("[^a-zA-Z]", ""); }
        while (name.length() < 3 || name.length() > 30);
        return name;
    }

    public static String randomEmail(String firstname, String lastname) {
        return firstname.toLowerCase() + "." + lastname.toLowerCase() + "@example.com";
    }

    public static String randomValidPhone() {
        int length = 11 + random.nextInt(11);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    /** ---------------- Negative Bookings ---------------- */
    public static Booking createBookingWithShortFirstname(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        String shortName = faker.lorem().characters(1, 2).replaceAll("[^a-zA-Z]", "");
        b.setFirstname(shortName);
        b.setEmail(randomEmail(shortName, b.getLastname()));
        return b;
    }

    public static Booking createBookingWithLongFirstname(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        String longName = faker.lorem().characters(19, 25).replaceAll("[^a-zA-Z]", "");
        b.setFirstname(longName);
        b.setEmail(randomEmail(longName, b.getLastname()));
        return b;
    }

    public static Booking createBookingWithShortLastname(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        String shortName = faker.lorem().characters(1, 2).replaceAll("[^a-zA-Z]", "");
        b.setLastname(shortName);
        b.setEmail(randomEmail(b.getFirstname(), shortName));
        return b;
    }

    public static Booking createBookingWithLongLastname(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        String longName = faker.lorem().characters(31, 40).replaceAll("[^a-zA-Z]", "");
        b.setLastname(longName);
        b.setEmail(randomEmail(b.getFirstname(), longName));
        return b;
    }

    public static Booking createBookingWithInvalidPhone(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        b.setPhone(randomValidPhone().substring(0, 5));
        return b;
    }

    public static Booking createBookingWithTooLongPhone(String checkin, String checkout) {
        Booking b = createAvailableBooking(checkin, checkout);
        StringBuilder sb = new StringBuilder(randomValidPhone());
        while (sb.length() <= 21) sb.append(random.nextInt(10));
        b.setPhone(sb.toString());
        return b;
    }

    public static Booking createBookingWithInvalidRoomId() {
        String firstname = randomFirstname();
        String lastname = randomLastname();
        return new Booking(-1, firstname, lastname, true, randomEmail(firstname, lastname),
                randomValidPhone(), randomBookingDates());
    }

    /** ---------------- Random Dates ---------------- */
    public static BookingDates randomBookingDates() {
        LocalDate checkin = LocalDate.now().plusDays(random.nextInt(30));
        LocalDate checkout = checkin.plusDays(1 + random.nextInt(10));
        return new BookingDates(checkin.format(formatter), checkout.format(formatter));
    }

    /** ---------------- Default Dates ---------------- */
    public static String getDefaultCheckin() {
        return LocalDate.now().plusDays(1).format(formatter);
    }

    public static String getDefaultCheckout() {
        return LocalDate.now().plusDays(2).format(formatter);
    }
}
















