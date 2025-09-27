package com.amgqa.kata.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedBooking {
    private int bookingid;
    private Booking booking;
}
