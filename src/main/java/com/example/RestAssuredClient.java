package com.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient {
    public RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .build();
    }

    public static int getResponseCode(Response response) {
        return response.then().extract().statusCode();
    }

    public static <T> T getResponseBodyFieldByPath(Response response, String fieldPath) {
        return response.then().extract().path(fieldPath);

    }
}
