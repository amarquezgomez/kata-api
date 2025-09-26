package com.amgqa.kata.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    @BeforeAll
    public static void setup() {
        // Default target API
        String baseURI = System.getProperty("baseUri", "https://automationintesting.online/");
        // Optional: default basePath
        String basePath = System.getProperty("basePath", "/");

        RestAssured.baseURI = baseURI;
        RestAssured.basePath = basePath;

        // enable logging for all requests/responses
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
