package com.amgqa.kata.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDates {
    private String checkin;   // yyyy-MM-dd
    private String checkout;  // yyyy-MM-dd
}


