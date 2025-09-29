package com.amgqa.kata.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    private static RequestSpecification requestSpec;       // unauthenticated
    private static RequestSpecification bearerAuthSpec;    // Bearer token for POST
    private static RequestSpecification cookieAuthSpec;    // Cookie token for GET

    private static String authToken;
    private static long tokenTimestamp = 0;  // epoch millis

    private static final long TOKEN_EXPIRY_MS = 9 * 60 * 1000; // 9 minutes

    public static synchronized void init() {
        long now = System.currentTimeMillis();
        if (requestSpec != null && authToken != null && now - tokenTimestamp < TOKEN_EXPIRY_MS) {
            return; // already initialized and token still valid
        }

        // Base URL
        RestAssured.baseURI = System.getProperty("api.base", "https://automationintesting.online");

        // Default spec
        if (requestSpec == null) {
            requestSpec = given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");
        }

        try {
            // Username/password from environment or default
            String username = System.getenv().getOrDefault("API_USERNAME", "admin");
            String password = System.getenv().getOrDefault("API_PASSWORD", "password");

            String authBody = String.format("""
                    {
                        "username": "%s",
                        "password": "%s"
                    }
                    """, username, password);

            // Get token from /api/auth/login
            authToken = requestSpec
                    .body(authBody)
                    .when()
                    .post("/api/auth/login")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token");

            tokenTimestamp = now;

            // Bearer token spec (for POST /booking)
            bearerAuthSpec = given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + authToken);

            // Cookie token spec (for GET /booking/{id})
            cookieAuthSpec = given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .cookie("token", authToken);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize BaseApiTest request specs", e);
        }
    }

    // ---------------- Getters ----------------
    public static RequestSpecification getRequestSpec() {
        init();
        return requestSpec;
    }

    public static RequestSpecification getBearerAuthSpec() {
        init();
        return bearerAuthSpec;
    }

    public static RequestSpecification getCookieAuthSpec() {
        init();
        return cookieAuthSpec;
    }

    public static String getAuthToken() {
        init();
        return authToken;
    }
}


