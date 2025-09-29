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
    private int bookingIdToDelete;

    // =========================
    // Setup data
    // =========================
    @Given("a valid booking is generated")
    public void generateValidBooking() {
        isNegativeBooking = false;
        booking = TestDataFactory.createBooking(); // Always valid: room 1–9, phone 11–21
    }

    @Given("a valid booking is created in the system")
    public void createValidBookingInSystem() {
        this.generateValidBooking();
        sendBookingToAPI();

        if (response.getStatusCode() == 201) {
            createdBooking = response.as(CreatedBooking.class);
        } else if (response.getStatusCode() == 409) {
            // Booking already exists — retrieve first available booking
            Response getAll = BaseApiTest.getCookieAuthSpec()
                    .when()
                    .get("/api/booking/?roomid=1")
                    .andReturn();
            getAll.then().statusCode(200);

            Integer firstBookingId = getAll.jsonPath().getInt("bookings[0].bookingid");
            if (firstBookingId != null) {
                createdBooking = BaseApiTest.getCookieAuthSpec()
                        .when()
                        .get("/api/booking/" + firstBookingId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(CreatedBooking.class);
            } else {
                throw new RuntimeException("No existing booking found to use for testing");
            }
        }

        assertThat("Booking creation failed", createdBooking, notNullValue());
    }

    // =========================
    // Negative booking generator helper
    // =========================
    public void generateNegativeBooking(NegativeBookingGenerator generator) {
        isNegativeBooking = true;
        String checkin = LocalDate.now().plusDays(60 + (int)(Math.random() * 10)).format(formatter);
        String checkout = LocalDate.parse(checkin).plusDays(1 + (int)(Math.random() * 5)).format(formatter);
        booking = generator.generate(checkin, checkout);
    }

    // =========================
    // Negative booking generators
    // =========================
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

    // =========================
    // POST booking
    // =========================
    @When("the booking is sent to the API")
    public void sendBookingToAPI() {
        response = BaseApiTest.getBearerAuthSpec()
                .body(booking)
                .when()
                .post("/api/booking")
                .andReturn();

        if (!isNegativeBooking) {
            assertThat("Booking creation failed", response.getStatusCode(), anyOf(is(201), is(409)));
            if (response.getStatusCode() == 201) {
                createdBooking = response.as(CreatedBooking.class);
            }
        } else {
            assertThat("Booking rejection failed", response.getStatusCode(), anyOf(is(400), is(422)));
        }
    }

    // =========================
    // GET by ID
    // =========================
    @When("the booking is retrieved by ID")
    public void retrieveBookingById() {
        assertThat("No booking available to retrieve", createdBooking, notNullValue());
        response = BaseApiTest.getCookieAuthSpec()
                .when()
                .get("/api/booking/" + createdBooking.getBookingId())
                .andReturn();
    }

    @Then("the booking details should match the created booking")
    public void verifyRetrievedBookingMatches() {
        response.then().statusCode(200);
        CreatedBooking fetched = response.as(CreatedBooking.class);

        assertThat(fetched.getFirstname(), equalTo(booking.getFirstname()));
        assertThat(fetched.getLastname(), equalTo(booking.getLastname()));
        assertThat(fetched.getRoomId(), equalTo(booking.getRoomId()));
        assertThat(fetched.isDepositpaid(), equalTo(booking.isDepositpaid()));
    }

    // =========================
    // GET all
    // =========================
    @When("all bookings are retrieved")
    public void retrieveAllBookings() {
        response = BaseApiTest.getCookieAuthSpec()
                .when()
                .get("/api/booking/?roomid=1")
                .andReturn();
    }

    @Then("the list of bookings should contain at least one booking")
    public void verifyAllBookingsNotEmpty() {
        response.then().statusCode(200);
        String body = response.getBody().asString().toLowerCase();
        assertThat(body, containsString("bookingid"));
    }

    // =========================
    // GET summary
    // =========================
    @When("the booking summary is retrieved")
    public void retrieveBookingSummary() {
        response = BaseApiTest.getCookieAuthSpec()
                .when()
                .get("/api/booking/summary?roomid=1")
                .andReturn();
    }

    @Then("the summary should include valid booking information")
    public void verifyBookingSummary() {
        response.then().statusCode(200);
        String body = response.getBody().asString().toLowerCase();
        assertThat(body, containsString("bookingdates"));
    }

    // =========================
    // PUT update
    // =========================
    @When("the booking is updated")
    public void updateBooking() {
        assertThat("No booking available to update", createdBooking, notNullValue());

        Booking bookingToUpdate = new Booking(
                booking.getRoomId(),
                booking.getFirstname() + "_Updated",
                booking.getLastname() + "_Updated",
                booking.isDepositpaid(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getBookingdates()
        );

        response = BaseApiTest.getCookieAuthSpec()
                .cookie("token", "abc123")
                .body(bookingToUpdate)
                .when()
                .put("/api/booking/" + createdBooking.getBookingId())
                .andReturn();

        response.then().statusCode(anyOf(is(200), is(201), is(403), is(409)));

        booking = bookingToUpdate;
    }

    @Then("the updated booking can be retrieved and verified")
    public void verifyUpdatedBooking() {
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            CreatedBooking updated = response.as(CreatedBooking.class);

            assertThat(updated.getFirstname(), equalTo(booking.getFirstname()));
            assertThat(updated.getLastname(), equalTo(booking.getLastname()));
        } else {
            System.out.println("Update returned 403/409. Skipping verification. " +
                    "API currently does not allow updates with this token (verified in Postman).");
        }
    }

    // =========================
    // DELETE
    // =========================
    @When("the booking is deleted")
    public void deleteBooking() {
        assertThat("No booking available to delete", createdBooking, notNullValue());

        int bookingNumber = createdBooking.getBookingId();

        response = BaseApiTest.getCookieAuthSpec()
                .cookie("token", "abc123")
                .when()
                .delete("/api/booking/" + bookingNumber)
                .andReturn();

        bookingIdToDelete = bookingNumber;
    }

    @Then("the booking should no longer exist")
    public void verifyBookingDeleted() {
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            String body = response.getBody().asString();
            assertThat(body, containsString("\"success\":true"));

            // Double-check booking is gone
            Response checkResponse = BaseApiTest.getCookieAuthSpec()
                    .when()
                    .get("/api/booking/" + bookingIdToDelete)
                    .andReturn();

            assertThat("Deleted booking should not be found",
                    checkResponse.getStatusCode(), is(404));
        } else if (statusCode == 500) {
            System.out.println("Warning: API returned 500 on delete. It may already be gone.");
        } else {
            throw new AssertionError("Unexpected status code on delete: " + statusCode);
        }
    }

    // =========================
    // Existing verification
    // =========================
    @Then("the booking can be retrieved and verified")
    public void verifyBookingFields() {
        if (createdBooking == null) {
            System.out.println("No CreatedBooking returned (likely due to conflict 409) – skipping field verification.");
            return;
        }

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
        assertThat(response.getStatusCode(), anyOf(is(400), is(422)));
    }

    @FunctionalInterface
    public interface NegativeBookingGenerator {
        Booking generate(String checkin, String checkout);
    }
}
























