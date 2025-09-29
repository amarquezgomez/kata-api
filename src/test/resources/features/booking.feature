Feature: Booking API tests

  # Positive scenario
  Scenario: Create and verify a valid booking
    Given a valid booking is generated
    When the booking is sent to the API
    Then the booking can be retrieved and verified

  # Negative scenarios
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

  Scenario: Booking with invalid roomId
    Given a booking with invalid roomId is generated
    When the booking is sent to the API
    Then the API should reject the booking

