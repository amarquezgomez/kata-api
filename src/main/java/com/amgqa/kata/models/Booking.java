package com.amgqa.kata.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @JsonProperty("roomid")
    private Integer roomId;

    private String firstname;
    private String lastname;
    private boolean depositpaid;   // primitive boolean, always true/false
    private String email;
    private String phone;
    private BookingDates bookingdates;
}







