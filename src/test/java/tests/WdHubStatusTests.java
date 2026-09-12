package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class WdHubStatusTests extends TestBase{
    @Test
    public void statusMessageTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_schema.json"))
                .body("value.message", equalTo("Selenoid v3.0.16 built at 2026-09-09_03:31:02PM"));
    }

    @Test
    public void statusReadyTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_schema.json"))
                .body("value.ready", equalTo(true));
    }

    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void emptyPasswordStatusTest() {
        given()
                .log().all()
                .auth().basic("user1", "")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void invalidPasswordTest() {
        given()
                .log().all()
                .auth().basic("", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void invalidCredentialsTest() {
        given()
                .log().all()
                .auth().basic("invalid_login_test", "invalid_password_test")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }
}
