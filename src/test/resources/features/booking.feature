Feature: Booking API tests

  # ========================
  # Positive Scenarios
  # ========================

  Scenario: Create and verify a valid booking
    Given a valid booking is created in the system
    Then the booking can be retrieved and verified

  Scenario: Get an existing booking by ID
    Given a valid booking is created in the system
    When the booking is retrieved by ID
    Then the booking details should match the created booking

  Scenario: Get all bookings
    When all bookings are retrieved
    Then the list of bookings should contain at least one booking

  Scenario: Get booking summary
    When the booking summary is retrieved
    Then the summary should include valid booking information

  Scenario: Update an existing booking
    Given a valid booking is created in the system
    When the booking is updated
    Then the updated booking can be retrieved and verified

  Scenario: Delete an existing booking
    Given a valid booking is created in the system
    When the booking is deleted
    Then the booking should no longer exist

  # ========================
  # Negative Scenarios
  # ========================

  Scenario: Booking with too short firstname
    Given a booking with short firstname is generated
    When the booking is sent to the API
    Then the API should reject the booking

  Scenario: Booking with too long firstname
    Given a booking with long firstname is generated
    When the booking is sent to the API
    Then the API should reject the booking

  Scenario: Booking with too short lastname
    Given a booking with short lastname is generated
    When the booking is sent to the API
    Then the API should reject the booking

  Scenario: Booking with too long lastname
    Given a booking with long lastname is generated
    When the booking is sent to the API
    Then the API should reject the booking

  Scenario: Booking with invalid phone
    Given a booking with invalid phone is generated
    When the booking is sent to the API
    Then the API should reject the booking

  Scenario: Booking with too long phone
    Given a booking with too long phone is generated
    When the booking is sent to the API
    Then the API should reject the booking




