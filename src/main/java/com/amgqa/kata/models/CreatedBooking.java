package com.amgqa.kata.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // ← Added to ignore extra fields like "error"
public class CreatedBooking {

    @JsonProperty("bookingid")
    private Integer bookingId;

    @JsonProperty("roomid")
    private Integer roomId;

    private String firstname;
    private String lastname;

    @JsonProperty("depositpaid")
    private boolean depositpaid;  // match Booking for consistency

    private BookingDates bookingdates;
}




