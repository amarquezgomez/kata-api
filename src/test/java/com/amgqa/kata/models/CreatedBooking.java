package com.amgqa.kata.models;

public class CreatedBooking {
    private Integer bookingid;
    private Booking booking;

    public CreatedBooking() {}

    public CreatedBooking(Integer bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public Booking getBooking() {
        return booking;
    }
}
