package com.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.Method;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders for create an order")
    public static Response createOrderResponse(Order order) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.POST;
        String requestPath = ORDER_PATH;
        return getResponse(specification, requestType, requestPath, order);
    }

    @Step("Send PUT request to /api/v1/orders/cancel")
    public static Response cancelOrderResponse(OrderTrack orderTrack) {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.PUT;
        String requestPath = String.format("%s/%s", ORDER_PATH, "cancel");
        return getResponse(specification, requestType, requestPath, orderTrack);
    }

    @Step("Calling cancel order method in OrderClient")
    public static boolean cancelOrder(int orderTrack) {
        return cancelOrderResponse(OrderTrack.from(orderTrack))
                .then().assertThat().statusCode(200)
                .extract().path("ok");
    }

    @Step("Send GET request to /api/v1/orders for getting order list response")
    public static Response getOrderListResponse() {
        RequestSpecification specification = getBaseSpec();
        Method requestType = Method.GET;
        String requestPath = ORDER_PATH;
        Object requestBody = null;
        return getResponse(specification, requestType, requestPath, requestBody);
    }
}
