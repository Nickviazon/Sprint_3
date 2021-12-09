package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private int orderTrackField;

    @Parameterized.Parameter()
    public String[] color;

    @Parameterized.Parameter(1)
    public int expectedResponseCode;

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][] {
                {new String[] {"BLACK"}, 201}, // 1 цвет - чёрный
                {new String[] {"GREY"}, 201}, // 1 цвет - серый
                {new String[] {"BLACK", "GREY"}, 201}, // оба цвета
                {null, 201} // без выбора цвета
        };
    }

    @After
    public void tearDown() {
        /*
        Тесты падают тут
        Запрос на отмену заказа всегда даёт 400 "Недостаточно данных для поиска" одинаково на сайте, в постмане, в коде
        Если закомментить tearDown, то тесты успешно падают в pass
        */
        OrderClient.cancelOrder(orderTrackField);
    }

    @Test
    @DisplayName("Check order create")
    public void orderCreatedWith201ResponseAndTrackField() {
        Order order = Order.getRandomOrderWithExactColor(color);
        Response orderCreatedResponse = OrderClient.createOrderResponse(order);

        int actualResponseCode = orderCreatedResponse.getStatusCode();
        assertThat("Wrong response code, expected code is 201",
                actualResponseCode, is(expectedResponseCode)
        );

        orderTrackField = orderCreatedResponse.getBody().path("track");
        assertThat("Incorrect order track", orderTrackField, is(not(0)));
    }
}
