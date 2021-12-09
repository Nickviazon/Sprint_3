package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderListTest {

    @Test
    @DisplayName("Orders list contains orders")
    public void orderListResponseWith200CodeAndContainsOrders() {
        Response orderListResponse = OrderClient.getOrderListResponse();

        int actualResponseCode = orderListResponse.getStatusCode();
        assertThat("Wrong response code, expected code is 201", actualResponseCode, is(200));

        ArrayList<LinkedHashMap> actualOrderList = orderListResponse.getBody().path("orders");
        actualOrderList.forEach(order -> assertThat("Order.id is incorrect", order.get("id"), is(not(0))));
    }

}
