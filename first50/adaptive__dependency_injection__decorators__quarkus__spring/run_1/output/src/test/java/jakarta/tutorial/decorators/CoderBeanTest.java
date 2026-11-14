package jakarta.tutorial.decorators;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.tutorial.decorators.DecoratorsApplication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = DecoratorsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoderBeanTest {

    @LocalServerPort
    int port;

    @Test
    public void testEncodeFlow() {
        RestAssured.port = port;
        RestAssured.basePath = "/decorators";
        CookieFilter cookieFilter = new CookieFilter();

        Response getResp = RestAssured
                .given()
                .filter(cookieFilter)
                .when()
                .get("/index.xhtml")
                .then()
                .statusCode(200)
                .extract().response();

        String viewState = getResp.htmlPath()
                .getString("**.findAll { it.@name == 'jakarta.faces.ViewState' }[0].@value");

        assertThat("ViewState should not be null", viewState, notNullValue());

        Response postResp = RestAssured
                .given()
                .redirects().follow(true)
                .filter(cookieFilter)
                .formParam("encodeit:inputString", "aa2")
                .formParam("encodeit:transVal", "2")
                .formParam("encodeit:submit", "Encode")
                .formParam("encodeit_SUBMIT", "1")
                .formParam("jakarta.faces.ViewState", viewState)
                .when()
                .post("/index.xhtml")
                .then()
                .statusCode(200)
                .extract().response();

        String responseHtml = postResp.asString();

        assertThat(responseHtml,
                containsString("&quot;aa2&quot; becomes &quot;cc2&quot;, 3 characters in length"));
    }

}
