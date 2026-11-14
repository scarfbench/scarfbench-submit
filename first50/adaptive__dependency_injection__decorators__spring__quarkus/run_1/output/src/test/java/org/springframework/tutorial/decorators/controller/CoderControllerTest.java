package org.springframework.tutorial.decorators.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CoderControllerTest {

    @Test
    void getMainPage() {
        given()
                .when().get("/")
                .then().statusCode(200)
                .body(containsString("Decorated String Encoder"))
                .body(containsString("name=\"inputString\""));
    }

    @Test
    void shouldRejectInvalidPayload() {
        given().contentType("application/x-www-form-urlencoded")
                .formParam("inputString", "aa")
                .formParam("transVal", "124")
                .when().post("/encode")
                .then().statusCode(200)
                .body(containsString("The value must be between 0 and 26."))
                .body(containsString("name=\"inputString\""));
    }

    @Test
    void encodeString() {
        Response response = given().redirects().follow(false)
                .contentType("application/x-www-form-urlencoded")
                .formParam("inputString", "aa")
                .formParam("transVal", "2")
                .when().post("/encode");

        response.then().statusCode(303)
                .header("Location", endsWith("/decorators/"))
                .cookie("decorators-flash", notNullValue());
        given().cookie("decorators-flash", response.cookie("decorators-flash"))
                .when().get("/")
                .then().body(containsString("&quot;aa&quot; becomes &quot;cc&quot;, 2 characters in length"));
    }

    @Test
    void resetShouldRedirect() {
        given().redirects().follow(false)
                .when().post("/reset")
                .then().statusCode(303)
                .header("Location", endsWith("/decorators/"))
                .cookie("decorators-flash", notNullValue());
    }

}
