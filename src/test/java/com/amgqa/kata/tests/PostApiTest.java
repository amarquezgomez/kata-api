package com.amgqa.kata.tests;

import com.amgqa.kata.config.BaseApiTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostApiTest extends BaseApiTest {

    @Test
    public void  testGetSinglePost() {
        given()
                .when()
                    .get("/1")
                .then()
                    .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyOrNullString()))
                .body("body", not(emptyOrNullString()));
    }
}
