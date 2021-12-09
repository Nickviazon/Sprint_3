package com.example;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier";


    public boolean createCourier(Courier courier) {
        return createCourierResponse(courier).
                then().assertThat().statusCode(201).extract().path("ok");
    }

    public static Response createCourierResponse(Courier courier) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = COURIER_PATH;
        return getResponse(specification, requestType, COURIER_PATH, courier);
    }

    public int loginCourier(CourierCredentials credentials) {
        return loginCourierResponse(credentials)
                .then().assertThat().statusCode(200)
                .extract().path("id");
    }

    public static Response loginCourierResponse(CourierCredentials credentials) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = String.format("%s/%s", COURIER_PATH, "login/");
        return getResponse(specification, requestType, requestPath, credentials);
    }

    public boolean deleteCourier(int courierId) {
        return deleteCourierResponse(courierId)
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    public static Response deleteCourierResponse(int courierId) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.DELETE;
        String requestPath = String.format("%s/%s", COURIER_PATH, courierId);
        Object requestBody = null;
        return getResponse(specification, requestType, requestPath, requestBody);
    }

}
