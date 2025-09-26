package com.amgqa.kata.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    @BeforeAll
    public static void setup() {
        // Default target API
        String baseUri = System.getProperty("api.base", "https://automationintesting.online/");
        RestAssured.baseURI = baseUri;

        // enable logging for all requests/responses
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
