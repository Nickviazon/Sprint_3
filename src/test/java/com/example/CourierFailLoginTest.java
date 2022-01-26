package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CourierFailLoginTest {

    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameter(1)
    public int expectedStatusCode;

    @Parameterized.Parameter(2)
    public String expectedErrorMessage;

    @Parameterized.Parameters(name="{index}:{0}")
    public static Object[][] courierData() {
        return new Object[][] {
                {Courier.getCourierWithEmptyCredentials(), 400, "Недостаточно данных для входа"}, // логин и пароль == null - бросает 504
                {Courier.getCourierWithoutLogin(), 400, "Недостаточно данных для входа"}, // логин == null
                {Courier.getCourierWithoutPassword() ,400, "Недостаточно данных для входа"}, // пароль == null - бросает 504
                {Courier.getRandom(), 404, "Учетная запись не найдена"} // неверный логин-пароль - бросает
        };
    }
    
    @Test
    @DisplayName("Courier can't login")
    public void courierCantLogin() {
        Response loginCourierResponse = CourierClient.loginCourierResponse(CourierCredentials.from(courier));

        int actualStatusCode = loginCourierResponse.getStatusCode();
        assertThat("Wrong response code", actualStatusCode, is(expectedStatusCode));

        String actualErrorMessage = loginCourierResponse.getBody().path("message");
        assertThat("Wrong error message in response body", actualErrorMessage, containsString(expectedErrorMessage));
    }

}
