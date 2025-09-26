package com.amgqa.kata.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BookingDates {

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "checkin must be yyyy-MM-dd format")
    private String checkin;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "checkout must be yyyy-MM-dd format")
    private String checkout;

    public BookingDates() {}

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }
}
