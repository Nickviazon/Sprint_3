/*
    курьера можно создать;  +
    нельзя создать двух одинаковых курьеров; +
    чтобы создать курьера, нужно передать в ручку все обязательные поля; +
    запрос возвращает правильный код ответа; +
    успешный запрос возвращает ok: true; +
    если одного из полей нет, запрос возвращает ошибку; +
    если создать пользователя с логином, который уже есть, возвращается ошибка. +
*/

// TODO: подумать над выносом проверок сообщений в отдельные ТК
// TODO: подумать над параметризацией
package com.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courier.login != null && courier.password != null) {
            int courierId = courierClient.login(CourierCredentials.from(courier));
            courierClient.delete(courierId);
        }
    }

    @Test
    public void courierCanBeCreatedWithCredentials() {
        courier = Courier.getRandom();
        Response courierCreatedResponse = courierClient.createResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierCreatedResponse);
        boolean responseBody = CourierClient.getResponseBodyFieldByPath(courierCreatedResponse, "ok");
        assertThat("Courier not created",
                true, both(is(responseCode == 201)).and(is(responseBody)));
    }

    @Test
    public void courierCanBeCreatedWithRequiredFields() {
        courier = Courier.getCourierWithOnlyRequiredFields();
        Response courierCreatedResponse = courierClient.createResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierCreatedResponse);
        boolean responseBody = CourierClient.getResponseBodyFieldByPath(courierCreatedResponse, "ok");
        assertThat("Courier not created",
                true, both(is(responseCode == 201)).and(is(responseBody)));
    }

    @Test
    public void cantBeCreatedTwoIdenticalCouriers() {
        courier = Courier.getRandom();
        // Create first courier
        Response firstCreatedCourierResponse = courierClient.createResponse(courier);
        int firstCourierResponseCode = CourierClient.getResponseCode(firstCreatedCourierResponse);
        boolean firstResponseBody= CourierClient.getResponseBodyFieldByPath(firstCreatedCourierResponse, "ok");
        // Create second courier
        Response secondCourierCreatedResponse = courierClient.createResponse(courier);
        int secondCourierResponseCode = CourierClient.getResponseCode(secondCourierCreatedResponse);
        String secondResponseBody = CourierClient.getResponseBodyFieldByPath(secondCourierCreatedResponse, "message");

        assertThat("First courier wasn't created",
                true, both(is(firstCourierResponseCode == 201)).and(is(firstResponseBody)));
        assertThat("Second courier was created", 409, is(secondCourierResponseCode));
        assertThat("Body message is incorrect when attempting to create second identical Courier",
                secondResponseBody,
                containsString("Этот логин уже используется"));

    }

    @Test
    public void courierCantBeCreatedWithoutCredentials() {
        courier = Courier.getCourierWithEmptyCredentials();
        Response courierResponse = courierClient.createResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierResponse);
        String responseBody = CourierClient.getResponseBodyFieldByPath(courierResponse, "message");
        assertThat("Courier without credentials was created", 400, is(responseCode));
        assertThat("Body message is incorrect when attempting to create a courier without credentials",
                responseBody,
                containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void courierCantBeCreatedWithTheSameLogin() {
        courier = Courier.getRandom();
        Courier secondCourier = Courier.getCourierWithExactLogin(courier.login);
        // Create first courier
        Response firstCreatedCourierResponse = courierClient.createResponse(courier);
        int firstCourierResponseCode = CourierClient.getResponseCode(firstCreatedCourierResponse);
        boolean firstResponseBody= CourierClient.getResponseBodyFieldByPath(firstCreatedCourierResponse, "ok");
        // Create second courier
        Response secondCourierCreatedResponse = courierClient.createResponse(secondCourier);
        int secondCourierResponseCode = CourierClient.getResponseCode(secondCourierCreatedResponse);
        String secondResponseBody = CourierClient.getResponseBodyFieldByPath(secondCourierCreatedResponse, "message");

        assertThat("First courier wasn't created",
                true, both(is(firstCourierResponseCode == 201)).and(is(firstResponseBody)));
        assertThat("Second courier was created", 409, is(secondCourierResponseCode));
        assertThat("Body message is incorrect when attempting to create second identical Courier",
                secondResponseBody,
                containsString("Этот логин уже используется"));
    }
}
