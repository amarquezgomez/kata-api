package com.amgqa.kata.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @NotNull
    private int roomid;

    @NotBlank
    @Size(min = 3, max = 18)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 30)
    private String lastname;

    @NotNull
    private boolean depositpaid;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 11, max = 21)
    private String phone;

    @NotNull
    private BookingDates bookingdates;
}
