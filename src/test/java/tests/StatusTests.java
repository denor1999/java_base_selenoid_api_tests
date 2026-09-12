package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTests extends TestBase{

    @Test
    public void totalAmountTest() {
        get("/status")
                .then()
                .body("total", is(25));
    }

    @Test
    public void totalAmountTestWithResponseLogs() {
        get("/status")
                .then()
                .log().all()
                .body("total", is(25));
    }

    @Test
    public void totalAmountTestWithAllLogs() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .body("total", is(25));
    }

    @Test
    public void statusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void browserVersionsTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("browsers.chrome", hasKey("151.0"))
                .body("browsers.chrome", hasKey("152.0"));
    }

    @Test
    public void requiredKeysTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("total"))
                .body("", hasKey("used"))
                .body("", hasKey("queued"))
                .body("", hasKey("pending"))
                .body("", hasKey("browsers"));
    }

    @Test
    public void statusSchemaTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));
    }

    @Test
    public void bestTotalAmountTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"))
                .body("total", is(25));
    }
}
