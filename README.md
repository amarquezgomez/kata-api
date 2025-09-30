# Kata-api testing in Java

- [Java 24](https://www.oracle.com/java/)
- [Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Cucumber](https://cucumber.io/)
- [Rest-Assured](https://rest-assured.io/)
- [Jackson](https://github.com/FasterXML/jackson)

Target API: [automationintesting.online](https://automationintesting.online/)

---

##  📁 Project Structure
```
src/
├─ main/java/com/amgqa/kata/
│  ├─ config/
│  │  └─ BaseApiTest.java          # API configuration and specs
│  ├─ models/
│  │  ├─ Booking.java              # Booking request model
│  │  ├─ CreatedBooking.java       # Booking response model
│  │  └─ BookingDates.java         # Booking date model
├─ test/java/com/amgqa/kata/
│  ├─ steps/
│  │  ├─ BookingSteps.java         # Step definitions (positive and negative)
│  │  └─ Hooks.java                # Cucumber hooks for setup
│  └─ utils/
│     └─ TestDataFactory.java      # Test data generators (valid, negative, boundary)
src/test/resources/
└─ features/
   └─ booking.feature              # Cucumber feature file for Booking scenarios
pom.xml                             # Maven dependencies and build configuration

```


---

## Features Covered

- **Positive scenarios:**
    - Create a booking
    - Retrieve booking by ID
    - Retrieve all bookings
    - Get booking summary
    - Update booking
    - Delete booking

- **Negative scenarios:**
    - Booking with short/long firstname
    - Booking with short/long lastname
    - Booking with invalid or too long phone

---

## Setup & Running Tests

### Requirements
- Java 17+
- Maven 3.8+
- Internet access for dependency download

### Run Tests
```bash
# Clone the repository
git clone https://github.com/amarquezgomez/kata-api
cd kata-api-tests

# Run all Cucumber tests
mvn clean test
```
- Test reports are available in target/cucumber-reports.

- Logs will indicate any API response time warnings (>5s) or skipped steps due to API limitations.

# Key Features

- Reusable Step Definitions: Single methods for repeated actions (create, update, retrieve, delete)

- Data Factories: Generate valid, boundary, and negative booking data

- Cucumber Hooks: Automated API initialization before each test

- Comprehensive Assertions: Verify response fields match expectations

- Structured Codebase: Models, utils, config, and steps separated for maintainability

# Notes

- Designed as an exercise/demo framework with focus on maintainability and clarity

- The API is a demo, some operations (like duplicate creation) may return 409/500

- Negative scenarios validate API rejection codes (400 or 422)
---
# Author

- Alejandro Marquez
- a.marquezgomez@yahoo.com

---

## **Suggested Commit Log**

```text
commit 1: Initial project structure (src, pom.xml, resources)
commit 2: Added BaseApiTest configuration for RestAssured
commit 3: Created Booking models (Booking, CreatedBooking, BookingDates)
commit 4: Implemented TestDataFactory with valid, negative, and boundary generators
commit 5: Added BookingSteps positive scenarios (create, get, update, delete)
commit 6: Added negative booking scenarios (firstname/lastname/phone edge cases)
commit 7: Implemented Cucumber Hooks for API setup
commit 8: Fixed zero-argument constructor issue for Cucumber
commit 9: Cleaned code, added comments, structured methods, verified scenario coverage
commit 10: Added README.md with project overview, setup, and execution instructions
