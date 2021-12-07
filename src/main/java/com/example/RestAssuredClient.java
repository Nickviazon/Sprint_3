package com.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient {

    public RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .build();
    }

    public static Response getResponse(
            RequestSpecification specification,
            ResponseType responseType,
            String requestPath,
            Object requestBody) {
        RequestSpecification requestSpecification;
        if (requestBody != null) {
            requestSpecification = given().spec(specification).body(requestBody).when();
        } else {
            requestSpecification = given().spec(specification).when();
        }
        Response returnedResponse;
        switch (responseType) {
            case GET:
                returnedResponse = requestSpecification.get(requestPath);
                break;
            case POST:
                returnedResponse = requestSpecification.post(requestPath);
                break;
            case PUT:
                returnedResponse = requestSpecification.put(requestPath);
                break;
            case PATCH:
                returnedResponse = requestSpecification.patch(requestPath);
                break;
            case DELETE:
                returnedResponse =  requestSpecification.delete(requestPath);
                break;
            default:
                throw new IllegalArgumentException("Trying to calls unsupported request type");
        }
        return returnedResponse;
    }
}
