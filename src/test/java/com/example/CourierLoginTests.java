/*
    курьер может авторизоваться;
    для авторизации нужно передать все обязательные поля;
    система вернёт ошибку, если неправильно указать логин или пароль;
    если какого-то поля нет, запрос возвращает ошибку;
    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    успешный запрос возвращает id.
*/

package com.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTests {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    public void courierWithAllFieldsCanLogin() {
        Response loginCourierResponse = courierClient.loginCourierResponse(CourierCredentials.from(courier));
        int responseCode = loginCourierResponse.getStatusCode();
        int courierId = courierClient.getResponseBodyFieldByPath(loginCourierResponse, "id");
        assertThat("Courier don't login", responseCode, is(200));
        assertThat("Courier has incorrect Id", courierId, is(not(0)));
    }

    public void courierWithRequiredFieldsCanAuthorize() {

    }

    public void successfulLoginReturnsCouriersId() {

    }

    // TODO: параметризованный тест: неверный логин, неверный пароль
    public void loginWithIncorrectCredentialsReturnsNotFound() {
    }

    // TODO: параметризованый тест: все поля пусты, постой логин, пустой пароль
    public void loginWithoutAnyCredentialsReturnsBadRequest() {}





}
