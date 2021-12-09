package com.example;

import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier";


    @Step("Calling courier client method")
    public boolean createCourier(Courier courier) {
        return createCourierResponse(courier).
                then().assertThat().statusCode(201).extract().path("ok");
    }

    @Step("Send POST request to api/v1/courier for getting create courier response")
    public static Response createCourierResponse(Courier courier) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = COURIER_PATH;
        return getResponse(specification, requestType, COURIER_PATH, courier);
    }

    @Step("Calling courier login method")
    public int loginCourier(CourierCredentials credentials) {
        return loginCourierResponse(credentials)
                .then().assertThat().statusCode(200)
                .extract().path("id");
    }

    @Step("Send POST request to api/v1/courier/login for getting login courier response")
    public static Response loginCourierResponse(CourierCredentials credentials) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = String.format("%s/%s", COURIER_PATH, "login/");
        return getResponse(specification, requestType, requestPath, credentials);
    }

    @Step("Calling courier delete method")
    public boolean deleteCourier(int courierId) {
        return deleteCourierResponse(courierId)
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    @Step("Send DELETE request to api/v1/courier/{courierId} for getting delete courier response")
    public static Response deleteCourierResponse(int courierId) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.DELETE;
        String requestPath = String.format("%s/%s", COURIER_PATH, courierId);
        Object requestBody = null;
        return getResponse(specification, requestType, requestPath, requestBody);
    }

}
