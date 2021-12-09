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

    private Integer courierId;

    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameter(1)
    public int expectedStatusCode;

    @Parameterized.Parameter(2)
    public String expectedErrorMessage;

    @Parameterized.Parameters(name="{index}:{0}")
    public static Object[][] courierData() {
        return new Object[][] {
                {Courier.getCourierWithEmptyCredentials(), 400, "������������ ������ ��� �����"}, // ����� � ������ == null - ������� 504
                {Courier.getCourierWithoutLogin(), 400, "������������ ������ ��� �����"}, // ����� == null
                {Courier.getCourierWithoutPassword() ,400, "������������ ������ ��� �����"}, // ������ == null - ������� 504
                {Courier.getRandom(), 404, "������� ������ �� �������"} // �������� �����-������ - �������
        };
    }
    
    @Test
    @DisplayName("Courier can't login ")
    public void courierCantLogin() {
        Response loginCourierResponse = CourierClient.loginCourierResponse(CourierCredentials.from(courier));

        int actualStatusCode = loginCourierResponse.getStatusCode();
        assertThat("Wrong response code", actualStatusCode, is(expectedStatusCode));

        String actualErrorMessage = loginCourierResponse.getBody().path("message");
        assertThat("Wrong error message in response body", actualErrorMessage, containsString(expectedErrorMessage));
    }

}
