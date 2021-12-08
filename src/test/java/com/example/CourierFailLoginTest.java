package com.example;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CourierFailLoginTest {

    private CourierClient courierClient;
    private Integer courierId;

    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameter(1)
    public int expectedStatusCode;

    @Parameterized.Parameter(2)
    public String expectedErrorMessage;

    @Parameterized.Parameters(name="{index}: {0}")
    public static Object[][] courierData() {
        return new Object[][] {
                {Courier.getCourierWithEmptyCredentials(), 400, "������������ ������ ��� �����"},
                {Courier.getCourierWithoutLogin(), 400, "������������ ������ ��� �����"},
                {Courier.getCourierWithoutPassword() ,400, "������������ ������ ��� �����"},
                {Courier.getRandom(), 404, "������� ������ �� �������"}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void courierCantLogin() {
        Response loginCourierResponse = courierClient.loginCourierResponse(CourierCredentials.from(courier));
        int actualStatusCode = loginCourierResponse.getStatusCode();
        String actualErrorMessage = loginCourierResponse.getBody().path("message");
        assertThat("Wrong response code", actualStatusCode, is(expectedStatusCode));
        assertThat("Wrong error message in response body", actualErrorMessage, containsString(expectedErrorMessage));
    }

}
