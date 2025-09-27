package com.amgqa.kata.steps;

import com.amgqa.kata.config.BaseApiTest;
import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.CreatedBooking;
import com.amgqa.kata.utils.TestDataFactory;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingSteps extends BaseApiTest {

    private Response response;
    private Booking booking;
    private int bookingId;

    @Given("the Booking API base URI is set")
    public void setBaseUri() {
        // Already set in BaseApiTest
    }

    @When("I create a booking with valid data")
    public void createValidBooking() {
        booking = TestDataFactory.createValidBooking(1);
        response = given()
                .header("Content-Type", "application/json")
                .body(booking)
                .when()
                .post("/booking/");

        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Then("the booking is created successfully")
    public void validateBookingCreation() {
        response.then()
                .statusCode(200)
                .body("booking.firstname", equalTo(booking.getFirstname()))
                .body("booking.lastname", equalTo(booking.getLastname()))
                .body("booking.email", equalTo(booking.getEmail()));
    }

    @Then("I can retrieve the booking by ID")
    public void retrieveBookingById() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()))
                .body("lastname", equalTo(booking.getLastname()))
                .body("email", equalTo(booking.getEmail()));
    }

    @When("I create a booking with {string} set to {string}")
    public void createBookingWithInvalidField(String field, String value) {
        switch (field) {
            case "firstname":
                booking = value.equals("short")
                        ? TestDataFactory.createBookingWithShortFirstname(1)
                        : TestDataFactory.createBookingWithLongFirstname(1);
                break;
            case "lastname":
                booking = value.equals("short")
                        ? TestDataFactory.createBookingWithShortLastname(1)
                        : TestDataFactory.createBookingWithLongLastname(1);
                break;
            case "email":
                booking = TestDataFactory.createBookingWithInvalidEmail(1);
                break;
            case "phone":
                booking = value.equals("short")
                        ? TestDataFactory.createBookingWithShortPhone(1)
                        : TestDataFactory.createBookingWithLongPhone(1);
                break;
            case "checkout":
                booking = TestDataFactory.createBookingWithInvalidDates(1);
                break;
            case "roomId":
                booking = TestDataFactory.createBookingWithInvalidRoomId();
                break;
        }

        response = given()
                .header("Content-Type", "application/json")
                .body(booking)
                .when()
                .post("/booking/");
    }
}
