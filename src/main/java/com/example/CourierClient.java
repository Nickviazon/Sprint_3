package com.example;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier";

    public Response response;

    public Response createResponse(Courier courier) {
        return given()
                .spec(getBaseSpec()).body(courier)
                .when().post(COURIER_PATH);
    }

    public int login(CourierCredentials credentials) {

        response = given().spec(getBaseSpec()).body(credentials)
                .when()
                .post(String.format("%s/%s", COURIER_PATH, "login/"));
        return response.then().assertThat().statusCode(200)
                .extract().path("id");
    }

    public boolean delete(int courierId) {
        response = given()
                .spec(getBaseSpec())
                .when().delete(String.format("%s/%s", COURIER_PATH, courierId));
        return response.then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    public long getCouriersOrderCount(int courierId) {
        response = given()
                .spec(getBaseSpec())
                .when().get(String.format("%s/%s/%s", COURIER_PATH, courierId, "ordersCount"));
        return response.then().assertThat().statusCode(200)
                .extract().path("orderCount");
    }
}
