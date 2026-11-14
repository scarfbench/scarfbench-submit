package org.springframework.tutorial.encoder.controller;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
@QuarkusTest
public class CoderControllerTest {
 @Test void getMainPage() { given().when().get("/").then().statusCode(200).body(containsString("String Encoder")).body(containsString("inputString")); }
 @Test void shouldRejectInvalidPayload() { given().formParam("inputString","aa").formParam("transVal","124").when().post("/encode").then().statusCode(200).body(containsString("between 0 and 26")); }
 @Test void encodeString() {
   var response=given().redirects().follow(false).formParam("inputString","aa").formParam("transVal","2").when().post("/encode").then().statusCode(anyOf(is(302),is(303))).header("Location", endsWith("/")).cookie("encoderFlash").extract().response();
   given().cookie("encoderFlash",response.cookie("encoderFlash")).when().get("/").then().statusCode(200).body(containsString("cc"));
 }
 @Test void resetShouldRedirect() { given().redirects().follow(false).when().post("/reset").then().statusCode(anyOf(is(302),is(303))).header("Location",endsWith("/")).cookie("encoderFlash"); }
}

