package com.example;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier";


    public boolean createCourier(Courier courier) {
        return createCourierResponse(courier).
                then().assertThat().statusCode(201).extract().path("ok");
    }

    public Response createCourierResponse(Courier courier) {
        RequestSpecification specification = getBaseSpec();
        ResponseType responseType = ResponseType.POST;
        String requestPath = COURIER_PATH;
        return getResponse(specification, responseType, COURIER_PATH, courier);
    }

    public int loginCourier(CourierCredentials credentials) {
        return loginCourierResponse(credentials)
                .then().assertThat().statusCode(200)
                .extract().path("id");
    }

    public Response loginCourierResponse(CourierCredentials credentials) {
        RequestSpecification specification = getBaseSpec();
        ResponseType responseType = ResponseType.POST;
        String requestPath = String.format("%s/%s", COURIER_PATH, "login/");
        return getResponse(specification, responseType, requestPath, credentials);
    }

    public boolean deleteCourier(int courierId) {
        return deleteCourierResponse(courierId)
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    public Response deleteCourierResponse(int courierId) {
        RequestSpecification specification = getBaseSpec();
        ResponseType responseType = ResponseType.DELETE;
        String requestPath = String.format("%s/%s", COURIER_PATH, courierId);
        Object requestBody = null;
        return getResponse(specification, responseType, requestPath, requestBody);
    }
    /*public boolean delete(int courierId) {
        response = given()
                .spec(getBaseSpec())
                .when().delete(String.format("%s/%s", COURIER_PATH, courierId));
        return response.then().assertThat().statusCode(200)
                .extract().path("ok");
    }*/

    /*public long getCouriersOrderCount(int courierId) {
        response = given()
                .spec(getBaseSpec())
                .when().get(String.format("%s/%s/%s", COURIER_PATH, courierId, "ordersCount"));
        return response.then().assertThat().statusCode(200)
                .extract().path("orderCount");
    }*/
}
