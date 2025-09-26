package com.amgqa.kata.tests;

import com.amgqa.kata.config.BaseApiTest;
import com.amgqa.kata.models.Post;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostDeserializationTest extends BaseApiTest {

    @Test
    public void testDeserializedPost() {
        String postId = "1";

        Post post = RestAssured
                .get("/" + postId)
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        assertEquals(Integer.parseInt(postId), post.getId());
        assertEquals(1, post.getUserId());
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", post.getTitle());
    }
}
