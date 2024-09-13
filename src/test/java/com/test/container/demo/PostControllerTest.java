package com.test.container.demo;

import com.test.container.demo.model.Post;
import com.test.container.demo.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PostControllerTest extends AbstractTestConfig {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testHealth() {
        given().get("/hearbeat")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAddPost() {
        given().body("""
                        {
                            "title": "Test Containers",
                            "content": "Short post about using the test container for integration tesing"
                        }
                        """)
                .post("/posts")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("content", notNullValue())
                .body("likes", notNullValue());
    }
}
