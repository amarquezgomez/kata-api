package com.amgqa.kata.utils;

import com.amgqa.kata.config.BaseApiTest;
import io.restassured.response.Response;

import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;

public class RoomFactory {

    private static final AtomicInteger roomCounter = new AtomicInteger(101);
    private static final int MAX_ATTEMPTS = 5;

    public static int createRoom() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            int roomNumber = roomCounter.getAndIncrement();
            String jsonBody = buildRoomJson(roomNumber);

            try {
                Response response = given()
                        .spec(BaseApiTest.getCookieAuthSpec())
                        .header("Content-Type", "application/json")
                        .header("Accept", "*/*")
                        .header("Origin", "https://automationintesting.online")
                        .body(jsonBody)
                        .redirects().follow(false)  // important: disable automatic redirects
                        .post("/api/room/");

                int status = response.statusCode();

                if (status == 201 || status == 200) {
                    int roomId = response.jsonPath().getInt("roomId");
                    System.out.println("Room created successfully: roomNumber=" + roomNumber + ", roomId=" + roomId);
                    return roomId;
                } else {
                    System.err.println("Attempt " + attempt + " failed: status=" + status + ", body=" + response.getBody().asString());
                }

            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " threw exception: " + e.getMessage());
            }
        }

        throw new RuntimeException("Failed to create room after " + MAX_ATTEMPTS + " attempts");
    }

    private static String buildRoomJson(int roomNumber) {
        return String.format("""
                {
                  "roomName": "%d",
                  "type": "Suite",
                  "accessible": true,
                  "image": "https://automationintesting.online/images/postman-logo.png",
                  "description": "This is room %d, dare you enter?",
                  "roomPrice": 100,
                  "features": ["WiFi","Safe"]
                }
                """, roomNumber, roomNumber);
    }
}














