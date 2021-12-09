package com.example;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.Method;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    public static Response createOrderResponse(Order order) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = ORDER_PATH;
        return getResponse(specification, requestType, requestPath, order);
    }

    public static Response cancelOrderResponse(OrderTrack orderTrack) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.PUT;
        String requestPath = String.format("%s/%s", ORDER_PATH, "cancel");
        return getResponse(specification, requestType, requestPath, orderTrack);
    }

    public static boolean cancelOrder(int orderTrack) {
        return cancelOrderResponse(OrderTrack.from(orderTrack))
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }
}
