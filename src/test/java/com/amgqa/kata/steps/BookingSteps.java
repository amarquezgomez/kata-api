package com.amgqa.kata.steps;

import com.amgqa.kata.models.Booking;
import com.amgqa.kata.models.BookingDates;
import com.amgqa.kata.models.CreatedBooking;
import com.amgqa.kata.config.BaseApiTest;
import com.amgqa.kata.utils.TestDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingSteps {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Booking booking;
    private Response response;
    private CreatedBooking createdBooking;
    private boolean isNegativeBooking = false;

    @Given("a valid booking is generated")
    public void generateValidBooking() {
        isNegativeBooking = false;
        booking = TestDataFactory.createBooking();
    }

    public void generateNegativeBooking(NegativeBookingGenerator generator) {
        isNegativeBooking = true;
        String checkin = LocalDate.now().plusDays(60 + (int)(Math.random() * 10)).format(formatter);
        String checkout = LocalDate.parse(checkin).plusDays(1 + (int)(Math.random() * 5)).format(formatter);
        booking = generator.generate(checkin, checkout);
    }

    @Given("a booking with short firstname is generated")
    public void bookingWithShortFirstname() {
        generateNegativeBooking(TestDataFactory::createBookingWithShortFirstname);
    }

    @Given("a booking with long firstname is generated")
    public void bookingWithLongFirstname() {
        generateNegativeBooking(TestDataFactory::createBookingWithLongFirstname);
    }

    @Given("a booking with short lastname is generated")
    public void bookingWithShortLastname() {
        generateNegativeBooking(TestDataFactory::createBookingWithShortLastname);
    }

    @Given("a booking with long lastname is generated")
    public void bookingWithLongLastname() {
        generateNegativeBooking(TestDataFactory::createBookingWithLongLastname);
    }

    @Given("a booking with invalid phone is generated")
    public void bookingWithInvalidPhone() {
        generateNegativeBooking(TestDataFactory::createBookingWithInvalidPhone);
    }

    @Given("a booking with too long phone is generated")
    public void bookingWithTooLongPhone() {
        generateNegativeBooking(TestDataFactory::createBookingWithTooLongPhone);
    }

    @Given("a booking with invalid roomId is generated")
    public void bookingWithInvalidRoomId() {
        booking = TestDataFactory.createBookingWithInvalidRoomId();
    }

    @When("the booking is sent to the API")
    public void sendBookingToAPI() {
        response = BaseApiTest.getBearerAuthSpec()
                .body(booking)
                .when()
                .post("/api/booking/")
                .andReturn();

        if (!isNegativeBooking) {
            assertThat("Booking creation failed", response.getStatusCode(), anyOf(is(200), is(201)));
            createdBooking = response.as(CreatedBooking.class);
        }
    }

    @Then("the booking can be retrieved and verified")
    public void verifyBookingFields() {
        assertThat("Booking creation failed, no CreatedBooking returned", createdBooking, notNullValue());

        CreatedBooking fetched = BaseApiTest.getCookieAuthSpec()
                .when()
                .get("/api/booking/" + createdBooking.getBookingId())
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract()
                .as(CreatedBooking.class);

        assertThat(fetched.getFirstname(), equalTo(booking.getFirstname()));
        assertThat(fetched.getLastname(), equalTo(booking.getLastname()));
        assertThat(fetched.getRoomId(), equalTo(booking.getRoomId()));
        assertThat(fetched.isDepositpaid(), equalTo(booking.isDepositpaid()));

        BookingDates dates = fetched.getBookingdates();
        assertThat(dates, notNullValue());
        assertThat(dates.getCheckin(), equalTo(booking.getBookingdates().getCheckin()));
        assertThat(dates.getCheckout(), equalTo(booking.getBookingdates().getCheckout()));
    }

    @Then("the API should reject the booking")
    public void verifyBookingRejected() {
        assertThat(response.getStatusCode(), anyOf(is(400), is(422), is(409)));
    }

    @FunctionalInterface
    public interface NegativeBookingGenerator {
        Booking generate(String checkin, String checkout);
    }
}


















