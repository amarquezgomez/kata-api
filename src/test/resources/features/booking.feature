Feature: Booking API

  Background:
    Given the Booking API base URI is set

  Scenario: Create a valid booking
    When I create a booking with valid data
    Then the booking is created successfully
    And I can retrieve the booking by ID

  Scenario Outline: Create booking with invalid data
    When I create a booking with <field> set to <value>
    Then the API returns status code 400

    Examples:
      | field       | value                  |
      | firstname   | short                  |
      | firstname   | long                   |
      | lastname    | short                  |
      | lastname    | long                   |
      | email       | invalid-email          |
      | phone       | short                  |
      | phone       | long                   |
      | checkout    | before-checkin         |
      | roomId      | negative               |
