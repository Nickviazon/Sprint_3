package com.example;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec()).body(courier)
                .when().post(COURIER_PATH)
                .then().assertThat().statusCode(201)
                .extract().path("ok");
    }

    public int login(CourierCredentials credentials) {
        return given().spec(getBaseSpec()).body(credentials)
                .when()
                .post(String.format("%s/%s", COURIER_PATH, "login"))
                .then().assertThat().statusCode(200)
                .extract().path("id");
    }

    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when().delete(String.format("%s/%s", COURIER_PATH, courierId))
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    public long getCouriersOrderCount(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when().get(String.format("%s/%s/%s", COURIER_PATH, courierId, "ordersCount"))
                .then().assertThat().statusCode(200)
                .extract().path("orderCount");
    }
}
