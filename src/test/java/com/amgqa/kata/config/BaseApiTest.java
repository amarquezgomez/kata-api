package com.amgqa.kata.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    // Request specifications
    protected static RequestSpecification requestSpec;  // for unauthenticated requests
    protected static RequestSpecification authRequestSpec;  // for secured requests

    // Auth token
    protected static String authToken;

    @BeforeAll
    public static void setup() {
        // Base URI configurable via system property or default
        RestAssured.baseURI = System.getProperty("api.base", "https://automationintesting.online/");

        // Default request spec (no auth)
        requestSpec = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        // Authenticate once and setup authRequestSpec
        authenticate();
    }

    /**
     * Authenticate against /auth endpoint and setup authRequestSpec for secured requests
     */
    private static void authenticate() {
        String body = """
                {
                    "username": "admin",
                    "password": "password"
                }
                """;

        authToken = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Authenticated request spec for PUT/PATCH/DELETE
        authRequestSpec = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + authToken);
    }
}
