package com.amgqa.kata.tests;

import com.amgqa.kata.config.BaseApiTest;
import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.CreatedBooking;
import com.amgqa.kata.utils.TestDataFactory;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingApiTest extends BaseApiTest {

    @Test
    public void testCreateAndGetBooking() {
        // Generate dynamic test data
        Booking requestBooking = TestDataFactory.createValidBooking(1);

        // Act → Create booking
        CreatedBooking createdBooking =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBooking)
                .when()
                        .post("/booking/")
                .then()
                        .statusCode(201)
                        .extract()
                        .as(CreatedBooking.class);

        Integer bookingId = createdBooking.getBookingid();

        // Verify the created booking matches request data
        assertThat(createdBooking.getBooking().getFirstname(), equalTo(requestBooking.getFirstname()));
        assertThat(createdBooking.getBooking().getLastname(), equalTo(requestBooking.getLastname()));
        assertThat(createdBooking.getBooking().getEmail(), equalTo(requestBooking.getEmail()));

        // Fetch booking by ID
        Booking fetchedBooking =
                given()
                        .pathParam("id", bookingId)
                .when()
                        .get("/booking/{id}")
                .then()
                        .statusCode(200)
                        .extract()
                        .as(Booking.class);

        // Verify fetched booking matches the created booking
        assertThat(fetchedBooking.getFirstname(), equalTo(requestBooking.getFirstname()));
        assertThat(fetchedBooking.getLastname(), equalTo(requestBooking.getLastname()));
        assertThat(fetchedBooking.getEmail(), equalTo(requestBooking.getEmail()));
        assertThat(fetchedBooking.getBookingdates().getCheckin(), equalTo(requestBooking.getBookingdates().getCheckin()));
        assertThat(fetchedBooking.getBookingdates().getCheckout(), equalTo(requestBooking.getBookingdates().getCheckout()));
    }
}
